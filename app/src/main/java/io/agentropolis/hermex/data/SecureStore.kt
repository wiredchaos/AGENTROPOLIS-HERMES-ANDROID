package io.agentropolis.hermex.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import io.agentropolis.hermex.model.EndpointConfig

interface SecureStoreContract {
    fun saveConfig(config: EndpointConfig)
    fun loadConfig(): EndpointConfig
}

class SecureStore(context: Context) : SecureStoreContract {
    private val prefs: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveConfig(config: EndpointConfig) {
        prefs.edit()
            .putString(KEY_SERVER, config.serverUrl.trim())
            .putString(KEY_TOKEN, config.authToken.trim())
            .putString(KEY_CHAT, config.chatPath.trim())
            .putString(KEY_SESSIONS, config.sessionsPath.trim())
            .putString(KEY_PROFILE, config.profilePath.trim())
            .putString(KEY_PROJECTS, config.projectsPath.trim())
            .putString(KEY_SKILLS, config.skillsPath.trim())
            .putString(KEY_MEMORY, config.memoryPath.trim())
            .putString(KEY_TASKS, config.tasksPath.trim())
            .putString(KEY_APPROVALS, config.approvalsPath.trim())
            .putString(KEY_UPLOAD, config.uploadPath.trim())
            .apply()
    }

    override fun loadConfig(): EndpointConfig = EndpointConfig(
        serverUrl = prefs.getString(KEY_SERVER, "").orEmpty(),
        authToken = prefs.getString(KEY_TOKEN, "").orEmpty(),
        chatPath = prefs.getString(KEY_CHAT, "").orEmpty(),
        sessionsPath = prefs.getString(KEY_SESSIONS, "").orEmpty(),
        profilePath = prefs.getString(KEY_PROFILE, "").orEmpty(),
        projectsPath = prefs.getString(KEY_PROJECTS, "").orEmpty(),
        skillsPath = prefs.getString(KEY_SKILLS, "").orEmpty(),
        memoryPath = prefs.getString(KEY_MEMORY, "").orEmpty(),
        tasksPath = prefs.getString(KEY_TASKS, "").orEmpty(),
        approvalsPath = prefs.getString(KEY_APPROVALS, "").orEmpty(),
        uploadPath = prefs.getString(KEY_UPLOAD, "").orEmpty()
    )

    companion object {
        private const val PREF_NAME = "hermex_secure_store"
        private const val KEY_SERVER = "server_url"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_CHAT = "chat_path"
        private const val KEY_SESSIONS = "sessions_path"
        private const val KEY_PROFILE = "profile_path"
        private const val KEY_PROJECTS = "projects_path"
        private const val KEY_SKILLS = "skills_path"
        private const val KEY_MEMORY = "memory_path"
        private const val KEY_TASKS = "tasks_path"
        private const val KEY_APPROVALS = "approvals_path"
        private const val KEY_UPLOAD = "upload_path"
    }
}
