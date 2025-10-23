package com.manishjajoriya.moctale.presentation.exploreScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.components.ErrorMessageText
import com.manishjajoriya.moctale.presentation.components.MoviePoster
import com.manishjajoriya.moctale.presentation.exploreScreen.components.SectionTitle
import com.manishjajoriya.moctale.ui.theme.Pink

@Composable
fun ExploreScreen(
    paddingValues: PaddingValues,
    viewModel: ExploreViewModel,
    navController: NavController,
) {
  LaunchedEffect(Unit) { viewModel.fetchExploreData() }

  val loading by viewModel.loading.collectAsState()
  val exploreData by viewModel.exploreData.collectAsState()
  val data = exploreData
  val isRefreshing by viewModel.isRefreshing.collectAsState()
  val error by viewModel.error.collectAsState()

  PullToRefreshBox(
      isRefreshing = isRefreshing,
      onRefresh = { viewModel.fetchExploreData(isRefreshCall = true) },
      modifier = Modifier.padding(paddingValues),
  ) {
    if (loading) {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Pink)
      }
    }
    error?.let { error ->
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ErrorMessageText(text = error, isPullToRefreshAvailable = true)
      }
    }
    Column(modifier = Modifier.padding(horizontal = 16.dp).padding(top = 20.dp).fillMaxSize()) {
      if (data != null) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(160.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          data.forEach { exploreItem ->
            item(span = { GridItemSpan(maxLineSpan) }) { SectionTitle(exploreItem) }

            itemsIndexed(exploreItem.contentList) { index, content ->
              MoviePoster(
                  content = content,
                  onClick = { slug ->
                    navController.navigate("${Routes.ContentScreen.route}/$slug")
                  },
              )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {}
          }
        }
      } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {}
      }
    }
  }
}
