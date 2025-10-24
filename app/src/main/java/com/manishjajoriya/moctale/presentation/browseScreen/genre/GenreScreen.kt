package com.manishjajoriya.moctale.presentation.browseScreen.genre

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.browseScreen.BrowseViewModel
import com.manishjajoriya.moctale.presentation.browseScreen.components.GenreBox
import com.manishjajoriya.moctale.presentation.browseScreen.components.SearchBar
import com.manishjajoriya.moctale.presentation.components.ErrorMessageText
import com.manishjajoriya.moctale.ui.theme.Pink

@Composable
fun GenreScreen(
    paddingValues: PaddingValues,
    viewModel: BrowseViewModel,
    navController: NavController,
) {
  val genres by viewModel.genre.collectAsState()
  val localGenre = genres
  val loading by viewModel.loading.collectAsState()
  val isRefreshing by viewModel.isRefreshing.collectAsState()
  val error by viewModel.error.collectAsState()
  var searchText by remember { mutableStateOf("") }

  LaunchedEffect(Unit) { viewModel.fetchGenres() }

  PullToRefreshBox(
      isRefreshing = isRefreshing,
      onRefresh = { viewModel.fetchGenres(isRefreshCall = true) },
      modifier = Modifier.padding(paddingValues),
  ) {
    if (localGenre != null) {
      LazyColumn(
          modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
          verticalArrangement = Arrangement.spacedBy(20.dp),
      ) {
        item {}
        item {
          SearchBar(
              value = searchText,
              placeHolderText = "Search genre",
              onValueChange = { searchText = it },
          )
        }
        localGenre
            .filter { it.name.startsWith(searchText.trim(), ignoreCase = true) }
            .forEach { genre ->
              item {
                GenreBox(genre = genre) {
                  navController.navigate(
                      Routes.BrowseScreen.route + "/genre/${genre.slug}/${genre.name}"
                  )
                }
              }
            }

        item {}
      }
    } else {
      LazyColumn(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        if (loading) {
          item { CircularProgressIndicator(color = Pink) }
        }
        error?.let { error -> item { ErrorMessageText(error, isPullToRefreshAvailable = true) } }
      }
    }
  }
}
