package com.manishjajoriya.moctale.presentation.browseScreen.country

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.browseScreen.BrowseViewModel
import com.manishjajoriya.moctale.presentation.browseScreen.components.CountryBox

@Composable
fun CountryScreen(
    paddingValues: PaddingValues,
    viewModel: BrowseViewModel,
    navController: NavController,
) {
  LaunchedEffect(Unit) { viewModel.fetchCountries() }
  val countries by viewModel.countries.collectAsState()
  countries?.let { countries ->
    Column(modifier = Modifier.padding(paddingValues).padding(horizontal = 12.dp)) {
      LazyVerticalGrid(
          columns = GridCells.Adaptive(160.dp),
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          verticalArrangement = Arrangement.spacedBy(20.dp),
      ) {
        item(span = { GridItemSpan(maxLineSpan) }) {}
        itemsIndexed(countries) { index, country ->
          CountryBox(country) {
            navController.navigate(
                Routes.BrowseScreen.route + "/country/${country.slug}/${country.name}"
            )
          }
        }
        item(span = { GridItemSpan(maxLineSpan) }) { Spacer(modifier = Modifier.height(20.dp)) }
      }
    }
  }
}
