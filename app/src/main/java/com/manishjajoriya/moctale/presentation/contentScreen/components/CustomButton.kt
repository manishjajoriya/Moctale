package com.manishjajoriya.moctale.presentation.contentScreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun CustomButton(modifier: Modifier = Modifier, icon: Int, title: String, color: Color, onClick: () -> Unit) {
  Button(
      modifier = modifier,
      onClick = onClick,
      colors = ButtonDefaults.buttonColors(containerColor = color),
  ) {
    Icon(painter = painterResource(icon), contentDescription = null, tint = Color.White)
    Spacer(modifier = Modifier.width(4.dp))
    Text(
        text = title,
        color = Color.White,
        style = Typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
    )
  }
}
