package io.agentropolis.hermex.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val HermexColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = Smoke,
    tertiary = NeonMagenta,
    background = NoirBlack,
    surface = NoirSurface
)

@Composable
fun HermexTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = HermexColorScheme,
        content = content
    )
}
