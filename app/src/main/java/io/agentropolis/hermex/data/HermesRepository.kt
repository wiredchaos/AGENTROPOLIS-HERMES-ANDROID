package io.agentropolis.hermex.data

import android.content.ContentResolver
import android.net.Uri
import io.agentropolis.hermex.model.EndpointConfig
import io.agentropolis.hermex.model.UiListItem
import io.agentropolis.hermex.model.UiMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class HermesRepository(private val secureStore: SecureStoreContract) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
        coerceInputValues = true
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    fun loadConfig(): EndpointConfig = secureStore.loadConfig()

    fun saveConfig(config: EndpointConfig) = secureStore.saveConfig(config)

    suspend fun healthCheck(config: EndpointConfig): Result<String> = withContext(Dispatchers.IO) {
        val base = config.serverUrl.takeIf { it.startsWith("https://") }
            ?: return@withContext Result.failure(IllegalArgumentException("Use a secure HTTPS URL."))

        val request = Request.Builder().url(base).headerIfToken(config.authToken).get().build()
        runCatching {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Server responded ${response.code}")
                "Connected to $base"
            }
        }
    }

    suspend fun fetchList(config: EndpointConfig, path: String): Result<List<UiListItem>> = withContext(Dispatchers.IO) {
        executeGet(config, path).map { body -> parseListItems(body) }
    }

    suspend fun fetchChat(config: EndpointConfig, path: String): Result<List<UiMessage>> = withContext(Dispatchers.IO) {
        executeGet(config, path).map { body -> parseChatItems(body) }
    }

    suspend fun sendChat(config: EndpointConfig, path: String, message: String): Result<Unit> = withContext(Dispatchers.IO) {
        val url = buildUrl(config.serverUrl, path)
            ?: return@withContext Result.failure(IllegalArgumentException("Chat endpoint path is required."))

        val payload = buildJsonObject { put("message", JsonPrimitive(message)) }.toString()
        val request = Request.Builder()
            .url(url)
            .headerIfToken(config.authToken)
            .post(payload.toRequestBody("application/json".toMediaType()))
            .build()

        runCatching {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Send failed: ${response.code}")
            }
        }
    }

    suspend fun uploadFile(
        config: EndpointConfig,
        path: String,
        contentResolver: ContentResolver,
        uri: Uri
    ): Result<String> = withContext(Dispatchers.IO) {
        val url = buildUrl(config.serverUrl, path)
            ?: return@withContext Result.failure(IllegalArgumentException("Upload endpoint path is required."))
        val bytes = contentResolver.openInputStream(uri)?.readBytes()
            ?: return@withContext Result.failure(IllegalStateException("Could not read file."))

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                "upload.bin",
                bytes.toRequestBody("application/octet-stream".toMediaType())
            )
            .build()

        val request = Request.Builder()
            .url(url)
            .headerIfToken(config.authToken)
            .post(body)
            .build()

        runCatching {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Upload failed: ${response.code}")
                "Upload complete"
            }
        }
    }

    private suspend fun executeGet(config: EndpointConfig, path: String): Result<String> {
        val url = buildUrl(config.serverUrl, path)
            ?: return Result.failure(IllegalArgumentException("Endpoint path is required."))

        val request = Request.Builder().url(url).headerIfToken(config.authToken).get().build()
        return runCatching {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Request failed: ${response.code}")
                response.body?.string().orEmpty()
            }
        }
    }

    private fun buildUrl(base: String, path: String): String? {
        val cleanBase = base.trim().trimEnd('/')
        val cleanPath = path.trim()
        if (!cleanBase.startsWith("https://") || cleanPath.isBlank()) return null
        return if (cleanPath.startsWith("http://") || cleanPath.startsWith("https://")) cleanPath
        else "$cleanBase/${cleanPath.trimStart('/')}"
    }

    internal fun parseListItems(body: String): List<UiListItem> {
        val root = runCatching { json.parseToJsonElement(body) }.getOrNull() ?: return emptyList()
        val sourceArray = when (root) {
            is JsonArray -> root
            is JsonObject -> root.findFirstArray()
            else -> null
        } ?: return emptyList()

        return sourceArray.mapIndexedNotNull { index, element ->
            val obj = element as? JsonObject ?: return@mapIndexedNotNull null
            val id = obj.pickString("id", "session_id", "uuid") ?: "item-$index"
            val title = obj.pickString("title", "name", "label")
                ?: obj.pickString("id", "session_id")
                ?: "Untitled"
            val subtitle = obj.pickString("summary", "description", "status") ?: ""
            UiListItem(id = id, title = title, subtitle = subtitle)
        }
    }

    internal fun parseChatItems(body: String): List<UiMessage> {
        val root = runCatching { json.parseToJsonElement(body) }.getOrNull() ?: return emptyList()
        val sourceArray = when (root) {
            is JsonArray -> root
            is JsonObject -> root.findFirstArray()
            else -> null
        } ?: return emptyList()

        return sourceArray.mapNotNull { element ->
            val obj = element as? JsonObject ?: return@mapNotNull null
            val content = obj.pickString("content", "message", "text") ?: return@mapNotNull null
            val role = obj.pickString("role", "author", "sender") ?: "assistant"
            UiMessage(role = role, content = content)
        }
    }

    private fun JsonObject.findFirstArray(): JsonArray? {
        val candidates = listOf("items", "data", "results", "sessions", "messages", "memories", "tasks")
        return candidates.firstNotNullOfOrNull { key -> this[key]?.jsonArrayOrNull() }
            ?: values.firstNotNullOfOrNull { it.jsonArrayOrNull() }
    }

    private fun JsonElement.jsonArrayOrNull(): JsonArray? = this as? JsonArray

    private fun JsonObject.pickString(vararg keys: String): String? =
        keys.firstNotNullOfOrNull { key ->
            val value = this[key] as? JsonPrimitive
            value?.contentOrNull?.takeIf { it.isNotBlank() }
        }

    private fun Request.Builder.headerIfToken(token: String): Request.Builder {
        if (token.isNotBlank()) {
            val sanitized = token.trim()
            val authValue = if (sanitized.startsWith("Bearer ", ignoreCase = true)) sanitized else "Bearer " + sanitized
            header("Authorization", authValue)
        }
        return this
    }
}
