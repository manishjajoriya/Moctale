package com.manishjajoriya.moctale.presentation.exploreScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.model.explore.ExploreItem
import com.manishjajoriya.moctale.presentation.components.MoviePoster
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun ExploreScreen(modifier: Modifier = Modifier, exploreViewModel: ExploreViewModel) {
  val loading by exploreViewModel.loading.collectAsState()
  val exploreData by exploreViewModel.exploreData.collectAsState()

  LaunchedEffect(Unit) { exploreViewModel.fetchExploreData() }

  if (loading) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      CircularProgressIndicator()
    }
  }

  exploreData?.let { data ->
    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
      data.forEach { exploreItem ->
        item(span = { GridItemSpan(maxLineSpan) }) { SectionTitle(exploreItem) }
        itemsIndexed(exploreItem.contentList) { index, content -> MoviePoster(content = content) }
      }
    }
  }
}

@Composable
fun SectionTitle(exploreItem: ExploreItem) {
  Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
  ) {
    AsyncImage(
        model = exploreItem.icon,
        contentDescription = exploreItem.name,
        modifier = Modifier.size(32.dp).padding(end = 8.dp),
    )
    Text(
        text = exploreItem.name,
        style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
    )
  }
}
