package io.agentropolis.hermex.viewmodel

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.agentropolis.hermex.data.HermesRepository
import io.agentropolis.hermex.model.EndpointConfig
import io.agentropolis.hermex.model.UiListItem
import io.agentropolis.hermex.model.UiMessage
import kotlinx.coroutines.launch

class AppViewModel(private val repository: HermesRepository) : ViewModel() {
    var config by mutableStateOf(repository.loadConfig())
        private set

    var statusMessage by mutableStateOf("Beta build: configure secure self-hosted server endpoints.")
        private set

    var chatMessages by mutableStateOf<List<UiMessage>>(emptyList())
        private set

    var sessions by mutableStateOf<List<UiListItem>>(emptyList())
        private set

    var profiles by mutableStateOf<List<UiListItem>>(emptyList())
        private set

    var projects by mutableStateOf<List<UiListItem>>(emptyList())
        private set

    var skills by mutableStateOf<List<UiListItem>>(emptyList())
        private set

    var memories by mutableStateOf<List<UiListItem>>(emptyList())
        private set

    var tasks by mutableStateOf<List<UiListItem>>(emptyList())
        private set

    var approvals by mutableStateOf<List<UiListItem>>(emptyList())
        private set

    fun updateConfig(newConfig: EndpointConfig) {
        config = newConfig
        repository.saveConfig(newConfig)
    }

    fun checkConnection() {
        viewModelScope.launch {
            statusMessage = repository.healthCheck(config)
                .fold(onSuccess = { it }, onFailure = { "Connection failed: ${it.message}" })
        }
    }

    fun loadChat() {
        viewModelScope.launch {
            repository.fetchChat(config, config.chatPath)
                .fold(
                    onSuccess = {
                        chatMessages = it
                        statusMessage = "Loaded ${it.size} chat message(s)"
                    },
                    onFailure = { statusMessage = "Load failed: ${it.message}" }
                )
        }
    }

    fun sendChat(message: String) {
        if (message.isBlank()) return
        viewModelScope.launch {
            statusMessage = repository.sendChat(config, config.chatPath, message)
                .fold(
                    onSuccess = {
                        loadChat()
                        "Message sent"
                    },
                    onFailure = { "Send failed: ${it.message}" }
                )
        }
    }

    fun loadSessions() = loadList(config.sessionsPath) { sessions = it }
    fun loadProfiles() = loadList(config.profilePath) { profiles = it }
    fun loadProjects() = loadList(config.projectsPath) { projects = it }
    fun loadSkills() = loadList(config.skillsPath) { skills = it }
    fun loadMemories() = loadList(config.memoryPath) { memories = it }
    fun loadTasks() = loadList(config.tasksPath) { tasks = it }
    fun loadApprovals() = loadList(config.approvalsPath) { approvals = it }

    fun uploadFile(contentResolver: ContentResolver, uri: Uri, endpointPath: String) {
        viewModelScope.launch {
            statusMessage = repository.uploadFile(config, endpointPath, contentResolver, uri)
                .fold(onSuccess = { it }, onFailure = { "Upload failed: ${it.message}" })
        }
    }

    private fun loadList(path: String, assign: (List<UiListItem>) -> Unit) {
        viewModelScope.launch {
            repository.fetchList(config, path)
                .fold(
                    onSuccess = {
                        assign(it)
                        statusMessage = "Loaded ${it.size} item(s)"
                    },
                    onFailure = { statusMessage = "Load failed: ${it.message}" }
                )
        }
    }

}
