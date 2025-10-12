package com.manishjajoriya.moctale.presentation.contentScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun ContentSection(title: String, showDivider: Boolean = true, content: @Composable () -> Unit) {
  Spacer(modifier = Modifier.height(12.dp))
  Text(
      text = title,
      modifier = Modifier.fillMaxWidth(),
      style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
  )
  Spacer(modifier = Modifier.height(12.dp))
  content()
  Spacer(modifier = Modifier.height(28.dp))
  if (showDivider) {
    HorizontalDivider(modifier = Modifier.fillMaxWidth())
  }
}
