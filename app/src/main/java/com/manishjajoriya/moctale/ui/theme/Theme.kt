package com.manishjajoriya.moctale.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MoctaleTheme(
    content: @Composable () -> Unit,
) {

  MaterialTheme(typography = Typography, content = content)
}
