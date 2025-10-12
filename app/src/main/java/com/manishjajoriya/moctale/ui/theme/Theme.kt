package com.manishjajoriya.moctale.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

val darkColorScheme = darkColorScheme(background = Background)

@Composable
fun MoctaleTheme(
    content: @Composable () -> Unit,
) {

  MaterialTheme(colorScheme = darkColorScheme, typography = Typography, content = content)
}
