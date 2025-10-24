package com.manishjajoriya.moctale.presentation.browseScreen.country

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.manishjajoriya.moctale.presentation.browseScreen.components.CountryBox
import com.manishjajoriya.moctale.presentation.browseScreen.components.SearchBar
import com.manishjajoriya.moctale.presentation.components.ErrorMessageText
import com.manishjajoriya.moctale.ui.theme.Pink

@Composable
fun CountryScreen(
    paddingValues: PaddingValues,
    viewModel: BrowseViewModel,
    navController: NavController,
) {
  val countries by viewModel.countries.collectAsState()
  val localCountries = countries
  val loading by viewModel.loading.collectAsState()
  val isRefreshing by viewModel.isRefreshing.collectAsState()
  val error by viewModel.error.collectAsState()
  var searchText by remember { mutableStateOf("") }
  LaunchedEffect(Unit) { viewModel.fetchCountries() }

  PullToRefreshBox(
      isRefreshing = isRefreshing,
      onRefresh = { viewModel.fetchCountries(isRefreshCall = true) },
      modifier = Modifier.padding(paddingValues),
  ) {
    if (localCountries != null) {
      LazyVerticalGrid(
          columns = GridCells.Adaptive(160.dp),
          modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          verticalArrangement = Arrangement.spacedBy(20.dp),
      ) {
        item(span = { GridItemSpan(maxLineSpan) }) {}
        item(span = { GridItemSpan(maxLineSpan) }) {
          SearchBar(value = searchText, placeHolderText = "Search country") { searchText = it }
        }

        localCountries
            .filter { it.name.startsWith(searchText.trim(), ignoreCase = true) }
            .forEach { country ->
              item {
                CountryBox(country) {
                  navController.navigate(
                      Routes.BrowseScreen.route + "/country/${country.slug}/${country.name}"
                  )
                }
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
        if (loading) {
          item { CircularProgressIndicator(color = Pink) }
        }
        error?.let { error -> item { ErrorMessageText(error, isPullToRefreshAvailable = true) } }
      }
    }
  }
}
