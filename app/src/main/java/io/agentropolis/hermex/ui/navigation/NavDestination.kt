package io.agentropolis.hermex.ui.navigation

enum class NavDestination(val route: String, val label: String) {
    Connect("connect", "Connect"),
    Chat("chat", "Chat"),
    Sessions("sessions", "Sessions"),
    Profiles("profiles", "Profiles"),
    Projects("projects", "Projects"),
    Skills("skills", "Skills"),
    Memory("memory", "Memory"),
    Tasks("tasks", "Tasks"),
    Approvals("approvals", "Approvals")
}
