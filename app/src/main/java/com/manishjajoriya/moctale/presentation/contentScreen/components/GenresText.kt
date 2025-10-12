package com.manishjajoriya.moctale.presentation.contentScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.manishjajoriya.moctale.model.content.Genre
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun GenresText(modifier: Modifier = Modifier, genre: Genre) {
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    Box(
        modifier =
            Modifier.size(12.dp).clip(CircleShape).background(Color(genre.color.toColorInt()))
    )
    Text(
        text = "${genre.name} (${genre.percentage}%)",
        modifier = Modifier.padding(start = 8.dp),
        style = Typography.labelLarge,
        color = Color.White.copy(.8f),
    )
  }
}
