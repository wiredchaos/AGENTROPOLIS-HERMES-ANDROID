package io.agentropolis.hermex.model

import kotlinx.serialization.Serializable

@Serializable
data class EndpointConfig(
    val serverUrl: String = "",
    val authToken: String = "",
    val chatPath: String = "",
    val sessionsPath: String = "",
    val profilePath: String = "",
    val projectsPath: String = "",
    val skillsPath: String = "",
    val memoryPath: String = "",
    val tasksPath: String = "",
    val approvalsPath: String = "",
    val uploadPath: String = ""
)

data class UiMessage(
    val role: String,
    val content: String,
    val timestampMs: Long = System.currentTimeMillis()
)

data class UiListItem(
    val id: String,
    val title: String,
    val subtitle: String = ""
)
