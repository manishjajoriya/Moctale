package com.manishjajoriya.moctale.presentation.contentScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.ui.theme.Background

@Composable
fun Banner(modifier: Modifier = Modifier, bannerUrl: String) {
  Box(modifier = modifier) {
    AsyncImage(
        model = bannerUrl,
        contentDescription = null,
        modifier = Modifier.height(270.dp),
        contentScale = ContentScale.Crop,
    )
    Box(
        modifier =
            Modifier.height(280.dp)
                .fillMaxWidth()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colorStops =
                                arrayOf(
                                    0.0f to Color.Transparent,
                                    0.5f to Color.Transparent,
                                    1.0f to Background,
                                )
                        )
                )
    )
  }
}
