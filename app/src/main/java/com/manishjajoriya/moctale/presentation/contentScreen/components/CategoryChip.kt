package com.manishjajoriya.moctale.presentation.contentScreen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.model.content.Category
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun CategoryChip(category: Category) {
  TextButton(
      onClick = {},
      shape = RoundedCornerShape(16.dp),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF171717)),
  ) {
    Text(
        text = category.name,
        style =
            Typography.labelMedium.copy(
                color = Color.White.copy(.9f),
                fontWeight = FontWeight.Bold,
            ),
    )
  }
}
