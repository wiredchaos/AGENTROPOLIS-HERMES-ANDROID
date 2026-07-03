package io.agentropolis.hermex.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.IconButton
import io.agentropolis.hermex.ui.navigation.NavDestination
import io.agentropolis.hermex.ui.screens.ChatScreen
import io.agentropolis.hermex.ui.screens.ConnectScreen
import io.agentropolis.hermex.ui.screens.DataListScreen
import io.agentropolis.hermex.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HermexApp(
    viewModel: AppViewModel,
    onPickFile: (endpointPath: String) -> Unit
) {
    var current by remember { mutableStateOf(NavDestination.Connect) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Hermex Beta", style = MaterialTheme.typography.headlineSmall)
                NavDestination.entries.forEach { destination ->
                    NavigationDrawerItem(
                        label = { Text(destination.label) },
                        selected = current == destination,
                        onClick = {
                            current = destination
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Hermex • ${current.label}") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) { Icon(Icons.Default.Menu, contentDescription = "Menu") }
                    },
                    actions = {
                        TextButton(onClick = { current = NavDestination.Connect }) { Text("BETA") }
                    }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                when (current) {
                    NavDestination.Connect -> ConnectScreen(
                        config = viewModel.config,
                        status = viewModel.statusMessage,
                        onSave = viewModel::updateConfig,
                        onCheck = viewModel::checkConnection
                    )

                    NavDestination.Chat -> ChatScreen(
                        messages = viewModel.chatMessages,
                        onRefresh = viewModel::loadChat,
                        onSend = viewModel::sendChat
                    )

                    NavDestination.Sessions -> DataListScreen(
                        title = "Sessions",
                        subtitle = "Session list and resume visibility from hermes-webui.",
                        items = viewModel.sessions,
                        onRefresh = viewModel::loadSessions
                    )

                    NavDestination.Profiles -> DataListScreen(
                        title = "Profiles",
                        subtitle = "Operator profile selector feed.",
                        items = viewModel.profiles,
                        onRefresh = viewModel::loadProfiles
                    )

                    NavDestination.Projects -> DataListScreen(
                        title = "Projects",
                        subtitle = "Project browser from your self-hosted runtime.",
                        items = viewModel.projects,
                        onRefresh = viewModel::loadProjects,
                        uploadButtonText = "Upload file",
                        onUpload = {
                            if (viewModel.config.uploadPath.isNotBlank()) {
                                onPickFile(viewModel.config.uploadPath)
                            }
                        }
                    )

                    NavDestination.Skills -> DataListScreen(
                        title = "Skills",
                        subtitle = "Skills browser.",
                        items = viewModel.skills,
                        onRefresh = viewModel::loadSkills
                    )

                    NavDestination.Memory -> DataListScreen(
                        title = "Memory + Insights",
                        subtitle = "Memory and insight viewer.",
                        items = viewModel.memories,
                        onRefresh = viewModel::loadMemories
                    )

                    NavDestination.Tasks -> DataListScreen(
                        title = "Tasks / Cron",
                        subtitle = "Task and cron visibility.",
                        items = viewModel.tasks,
                        onRefresh = viewModel::loadTasks
                    )

                    NavDestination.Approvals -> DataListScreen(
                        title = "Operator Approvals",
                        subtitle = "Agentropolis operator approval queue.",
                        items = viewModel.approvals,
                        onRefresh = viewModel::loadApprovals
                    )
                }
            }
        }
    }
}
