package com.manishjajoriya.moctale.presentation.contentScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun ProfileCircle(imageUrl: String?, name: String, label: String, onClick: () -> Unit) {
  Column(
      modifier = Modifier.clickable(onClick = onClick),
      horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    if (imageUrl != null) {
      AsyncImage(
          model = imageUrl,
          contentDescription = null,
          modifier = Modifier.clip(CircleShape).size(132.dp),
      )
    } else {
      Box(
          modifier = Modifier.size(132.dp).clip(CircleShape).background(Color(0xFF1f2937)),
          contentAlignment = Alignment.Center,
      ) {
        val initials =
            name
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.first().uppercaseChar() }
                .joinToString("")
        Text(initials, style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold))
      }
    }
    Text(
        text = name,
        style = Typography.labelLarge,
        modifier = Modifier.width(132.dp),
        textAlign = TextAlign.Center,
    )
    Text(
        text = label,
        style = Typography.bodySmall.copy(color = Color.Gray),
        modifier = Modifier.width(132.dp),
        textAlign = TextAlign.Center,
    )
  }
}
