package com.manishjajoriya.moctale.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun ErrorMessageText(text: String, isPullToRefreshAvailable: Boolean = false) {
  Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = Typography.titleMedium.copy(color = Color.Red.copy(.9f), fontSize = 18.sp),
    )
    if (isPullToRefreshAvailable)
        Text(
            text = "Swipe down to reload",
            style = Typography.titleMedium.copy(fontSize = 18.sp, color = Color.White.copy(.7f)),
        )
  }
}
