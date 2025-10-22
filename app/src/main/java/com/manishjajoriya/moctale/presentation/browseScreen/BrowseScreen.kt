package com.manishjajoriya.moctale.presentation.browseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.manishjajoriya.moctale.domain.model.browse.Type
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.browseScreen.components.FilterOut
import com.manishjajoriya.moctale.presentation.components.MoviePoster
import com.manishjajoriya.moctale.ui.theme.Background
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun BrowseScreen(
    browseSlug: String,
    categorySlug: String,
    categoryName: String,
    paddingValues: PaddingValues,
    viewModel: BrowseViewModel,
    navController: NavController,
) {
  LaunchedEffect(categorySlug) {
    viewModel.fetchBrowseData(browseSlug = browseSlug, category = categorySlug)
  }

  val browseDataFlow by viewModel.browseData.collectAsState()
  val browseDataItems = browseDataFlow?.collectAsLazyPagingItems()
  val totalItems by viewModel.totalItem.collectAsState()
  var selectedType by remember { mutableStateOf(Type.ALL) }
  var selectedDropDownIndex by remember { mutableIntStateOf(0) }

  LazyVerticalGrid(
      columns = GridCells.Adaptive(160.dp),
      modifier = Modifier.padding(paddingValues),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    item(span = { GridItemSpan(maxLineSpan) }) {
      Box(
          modifier =
              Modifier.fillMaxWidth()
                  .height(350.dp)
                  .background(
                      brush =
                          Brush.linearGradient(
                              listOf(Color(0xFF13223a), Color(0xFF0e1421), Background),
                              start = Offset(0f, 0f),
                              end = Offset(0f, Float.POSITIVE_INFINITY),
                          ),
                  ),
          contentAlignment = Alignment.Center,
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
              text = browseSlug.uppercase(),
              style = Typography.labelLarge.copy(color = Color.Gray),
          )
          Text(
              text = categoryName,
              style = Typography.displayMedium.copy(fontWeight = FontWeight.SemiBold),
          )
          Text(
              text = "$totalItems Total Items",
              style = Typography.titleMedium.copy(color = Color.White.copy(.7f)),
          )
        }
      }
    }
    item(span = { GridItemSpan(maxLineSpan) }) {
      FilterOut(
          selectedType,
          { selectedType = it },
          selectedDropDownIndex,
          { selectedDropDownIndex = it },
      )
    }
    browseDataItems?.let { items ->
      items(items.itemCount) { index ->
        Box(modifier = Modifier.padding(8.dp)) {
          browseDataItems[index]?.let { data ->
            MoviePoster(
                name = data.name,
                imageUrl = data.image,
                label = data.isShow.toString(),
                slug = data.slug,
            ) { slug ->
              navController.navigate(Routes.ContentScreen.route + "/$slug")
            }
          }
        }
      }
    }
  }
}
