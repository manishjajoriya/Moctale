package com.manishjajoriya.moctale.presentation.browseScreen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.browseScreen.BrowseViewModel
import com.manishjajoriya.moctale.presentation.browseScreen.components.SearchBar
import com.manishjajoriya.moctale.presentation.components.ErrorMessageText
import com.manishjajoriya.moctale.ui.theme.Pink
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun CategoriesScreen(
    paddingValues: PaddingValues,
    viewModel: BrowseViewModel,
    navController: NavController,
) {
  val categories by viewModel.categories.collectAsState()
  val localCategories = categories
  val loading by viewModel.loading.collectAsState()
  val isRefreshing by viewModel.isRefreshing.collectAsState()
  val error by viewModel.error.collectAsState()
  var searchText by remember { mutableStateOf("") }
  LaunchedEffect(Unit) { viewModel.fetchCategories() }

  PullToRefreshBox(
      isRefreshing = isRefreshing,
      onRefresh = { viewModel.fetchCategories(isRefreshCall = true) },
      modifier = Modifier.padding(paddingValues),
  ) {
    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
      if (localCategories != null) {
        val groupedCategories = localCategories.groupBy { it.name.first() }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          item(span = { GridItemSpan(maxLineSpan) }) {}
          item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Categories",
                style = Typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
            )
          }
          item(span = { GridItemSpan(maxLineSpan) }) {
            SearchBar(value = searchText, placeHolderText = "Search category") { searchText = it }
          }
          groupedCategories
              .mapValues { (_, categories) ->
                categories.filter { it.name.startsWith(searchText.trim(), ignoreCase = true) }
              }
              .filter { (_, categories) -> categories.isNotEmpty() }
              .forEach { char, category ->
                item(span = { GridItemSpan(maxLineSpan) }) {
                  Text(
                      text = char.uppercase(),
                      textAlign = TextAlign.Center,
                      style = Typography.displayLarge.copy(fontWeight = FontWeight.Bold),
                  )
                }
                itemsIndexed(category) { index, data ->
                  Text(
                      text = data.name,
                      modifier =
                          Modifier.clickable {
                                navController.navigate(
                                    "${Routes.BrowseScreen.route}/category/${data.slug}/${data.name}"
                                )
                              }
                              .clip(RoundedCornerShape(8.dp))
                              .background(Color(0xFF151515))
                              .padding(12.dp)
                              .fillMaxWidth(),
                      style = Typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                  )
                }
              }
          item(span = { GridItemSpan(maxLineSpan) }) {}
        }
      } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
          item {
            if (loading) CircularProgressIndicator(color = Pink)
            error?.let { error -> ErrorMessageText(text = error, isPullToRefreshAvailable = true) }
          }
        }
      }
    }
  }
}
