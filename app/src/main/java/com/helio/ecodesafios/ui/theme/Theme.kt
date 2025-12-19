package com.helio.ecodesafios.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF1B5E20),
    onPrimary = Color.White,
    secondary = Color(0xFF66BB6A),
    background = Color(0xFFF1F8E9),
    onBackground = Color(0xFF1B1B1B),
    error = Color(0xFFB00020),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF66BB6A),
    onPrimary = Color.Black,
    secondary = Color(0xFF81C784),
    background = Color(0xFF121212),
    onBackground = Color(0xFFF1F8E9),
    error = Color(0xFFCF6679),
)

@Composable
fun EcoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}


