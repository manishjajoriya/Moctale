package com.manishjajoriya.moctale.presentation.contentScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun ReviewPercentage(
    color: Color,
    title: String,
    percentage: String,
) {
  Row(modifier = Modifier.fillMaxWidth()) {
    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
      Box(modifier = Modifier.clip(CircleShape).size(12.dp).background(color))
      Spacer(modifier = Modifier.width(4.dp))
      Text(text = title, style = Typography.bodyMedium.copy(color = Color.White.copy(.6f)))
    }
    Text(text = "$percentage%", style = Typography.bodyMedium.copy(fontWeight = FontWeight.Medium))
  }
}
