package com.manishjajoriya.moctale.presentation.personScreen.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun PersonPic(modifier: Modifier = Modifier, imageUrl: String) {
  AsyncImage(
      model = imageUrl,
      contentDescription = null,
      modifier = modifier.size(120.dp).clip(CircleShape),
  )
}
