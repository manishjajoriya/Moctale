package com.manishjajoriya.moctale.domain.model.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun DateText(day: String, date: String, month: String) {
  Column(
      horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
        text = day,
        style = Typography.labelLarge,
    )
    Text(
        text = date,
        style = Typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
    )
    Text(
        text = month.uppercase(),
        style = Typography.labelSmall.copy(color = Color.Gray),
    )
  }
}
