package com.manishjajoriya.moctale.presentation.browseScreen.genre

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.browseScreen.BrowseViewModel
import com.manishjajoriya.moctale.ui.theme.Background
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun GenreScreen(
    paddingValues: PaddingValues,
    viewModel: BrowseViewModel,
    navController: NavController,
) {
  LaunchedEffect(Unit) { viewModel.fetchGenres() }

  val genres by viewModel.genre.collectAsState()
  genres?.let { genres ->
    LazyColumn(
        modifier =
            Modifier.padding(paddingValues)
                .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 60.dp)
                .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      itemsIndexed(genres) { index, genre ->
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
                                colors =
                                    listOf(Color(genre.color.toColorInt()).copy(.25f), Background),
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                            )
                    )
                    .height(200.dp)
                    .clickable(
                        onClick = {
                          navController.navigate(
                              Routes.BrowseScreen.route + "/genre/${genre.slug}/${genre.name}"
                          )
                        }
                    )
        ) {
          Text(
              text = genre.name,
              modifier = Modifier.padding(20.dp),
              style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
          )
        }
      }
    }
  }
}
