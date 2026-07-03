package io.agentropolis.hermex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import io.agentropolis.hermex.model.EndpointConfig
import io.agentropolis.hermex.model.UiListItem
import io.agentropolis.hermex.model.UiMessage

@Composable
fun ConnectScreen(
    config: EndpointConfig,
    status: String,
    onSave: (EndpointConfig) -> Unit,
    onCheck: () -> Unit
) {
    val state = remember(config) { mutableStateOf(config) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text("Hermex Android Beta", style = MaterialTheme.typography.headlineSmall)
            Text("Self-hosted, operator-controlled mobile command surface.")
            Text("BETA", color = MaterialTheme.colorScheme.tertiary)
        }
        item { EndpointField("Secure server URL", state.value.serverUrl) { state.value = state.value.copy(serverUrl = it) } }
        item {
            OutlinedTextField(
                value = state.value.authToken,
                onValueChange = { state.value = state.value.copy(authToken = it) },
                label = { Text("Auth token") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { EndpointField("Chat path", state.value.chatPath) { state.value = state.value.copy(chatPath = it) } }
        item { EndpointField("Sessions path", state.value.sessionsPath) { state.value = state.value.copy(sessionsPath = it) } }
        item { EndpointField("Profiles path", state.value.profilePath) { state.value = state.value.copy(profilePath = it) } }
        item { EndpointField("Projects path", state.value.projectsPath) { state.value = state.value.copy(projectsPath = it) } }
        item { EndpointField("Skills path", state.value.skillsPath) { state.value = state.value.copy(skillsPath = it) } }
        item { EndpointField("Memory path", state.value.memoryPath) { state.value = state.value.copy(memoryPath = it) } }
        item { EndpointField("Tasks path", state.value.tasksPath) { state.value = state.value.copy(tasksPath = it) } }
        item { EndpointField("Approvals path", state.value.approvalsPath) { state.value = state.value.copy(approvalsPath = it) } }
        item { EndpointField("Upload path", state.value.uploadPath) { state.value = state.value.copy(uploadPath = it) } }
        item {
            Button(onClick = { onSave(state.value) }, modifier = Modifier.fillMaxWidth()) {
                Text("Save")
            }
        }
        item {
            Button(onClick = onCheck, modifier = Modifier.fillMaxWidth()) {
                Text("Connect")
            }
        }
        item { Text(status, color = MaterialTheme.colorScheme.secondary) }
    }
}

@Composable
private fun EndpointField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ChatScreen(messages: List<UiMessage>, onRefresh: () -> Unit, onSend: (String) -> Unit) {
    val draft = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = onRefresh, modifier = Modifier.fillMaxWidth()) { Text("Refresh chat") }
        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(messages) { msg ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(msg.role.uppercase(), color = MaterialTheme.colorScheme.tertiary)
                        Text(msg.content)
                    }
                }
            }
        }
        OutlinedTextField(value = draft.value, onValueChange = { draft.value = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Message") })
        Button(onClick = { onSend(draft.value); draft.value = "" }, modifier = Modifier.fillMaxWidth()) { Text("Send") }
    }
}

@Composable
fun DataListScreen(
    title: String,
    subtitle: String,
    items: List<UiListItem>,
    onRefresh: () -> Unit,
    uploadButtonText: String? = null,
    onUpload: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(subtitle)
        Button(onClick = onRefresh, modifier = Modifier.fillMaxWidth()) { Text("Refresh") }
        if (uploadButtonText != null && onUpload != null) {
            Button(onClick = onUpload, modifier = Modifier.fillMaxWidth()) { Text(uploadButtonText) }
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(item.title)
                        if (item.subtitle.isNotBlank()) {
                            Text(item.subtitle, color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
        }
    }
}
