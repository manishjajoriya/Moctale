package com.manishjajoriya.moctale.presentation.browseScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.manishjajoriya.moctale.domain.model.browse.genre.Genre
import com.manishjajoriya.moctale.ui.theme.Background
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun GenreBox(genre: Genre, onClick: () -> Unit) {
  Row(
      modifier =
          Modifier.clip(RoundedCornerShape(12.dp))
              .fillMaxWidth()
              .border(
                  2.dp,
                  Color(genre.color.toColorInt()).copy(.5f),
                  shape = RoundedCornerShape(12.dp),
              )
              .background(
                  brush =
                      Brush.linearGradient(
                          colors = listOf(Color(genre.color.toColorInt()).copy(.25f), Background),
                          start = Offset(0f, 0f),
                          end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                      )
              )
              .height(200.dp)
              .clickable(onClick = onClick)
  ) {
    Text(
        text = genre.name,
        modifier = Modifier.padding(20.dp),
        style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
    )
  }
}
