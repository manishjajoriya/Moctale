package com.manishjajoriya.moctale.presentation.scheduleScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.manishjajoriya.moctale.domain.model.schedule.Category
import com.manishjajoriya.moctale.domain.model.schedule.DateText
import com.manishjajoriya.moctale.domain.model.schedule.MovieCategory
import com.manishjajoriya.moctale.domain.model.schedule.SeriesCategory
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.model.schedule.UiScheduleItem
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.components.MoviePoster
import com.manishjajoriya.moctale.presentation.scheduleScreen.components.FilterSelector
import com.manishjajoriya.moctale.presentation.scheduleScreen.components.TabSelector

@Composable
fun ScheduleScreen(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: ScheduleViewModel,
) {

  var selectedTimeFilter by remember { mutableStateOf(TimeFilter.TODAY) }
  var selectedCategory by remember { mutableStateOf(Category.ALL) }
  var selectedMovieCategory by remember { mutableStateOf(MovieCategory.IN_THEATRES) }
  var selectedSeriesCategory by remember { mutableStateOf(SeriesCategory.NEW_SHOW) }

  val schedulesFlows =
      remember(
          selectedTimeFilter,
          selectedCategory,
          selectedMovieCategory,
          selectedSeriesCategory,
      ) {
        when (selectedCategory) {
          Category.MOVIE ->
              viewModel.getGroupedScheduleFlow(selectedTimeFilter, selectedMovieCategory.value)
          Category.SERIES ->
              viewModel.getGroupedScheduleFlow(selectedTimeFilter, selectedSeriesCategory.value)
          else -> viewModel.getGroupedScheduleFlow(selectedTimeFilter, null)
        }
      }

  val groupedItem = schedulesFlows.collectAsLazyPagingItems()

  Column(modifier = Modifier.padding(paddingValues).padding(horizontal = 12.dp)) {

    // Time Filter
    TabSelector(selectedTimeFilter) { timeFilter ->
      selectedTimeFilter = timeFilter
      selectedCategory = Category.ALL
      selectedMovieCategory = MovieCategory.IN_THEATRES
      selectedSeriesCategory = SeriesCategory.NEW_SHOW
    }

    // Category
    if (selectedCategory == Category.ALL) {
      FilterSelector(fields = Category.entries, selectedFiled = selectedCategory, onClear = {}) {
          category ->
        selectedCategory = category
      }
    }

    // Movie Filter Options
    if (selectedCategory == Category.MOVIE) {
      FilterSelector(
          fields = MovieCategory.entries,
          selectedFiled = selectedMovieCategory,
          onClear = {
            selectedCategory = Category.ALL
            selectedMovieCategory = MovieCategory.IN_THEATRES
          },
      ) { category ->
        selectedMovieCategory = category
      }
    }

    // Series Filter Options
    if (selectedCategory == Category.SERIES) {
      FilterSelector(
          fields = SeriesCategory.entries,
          selectedFiled = selectedSeriesCategory,
          onClear = {
            selectedCategory = Category.ALL
            selectedSeriesCategory = SeriesCategory.NEW_SHOW
          },
      ) { category ->
        selectedSeriesCategory = category
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      items(
          groupedItem.itemCount,
          span = { index ->
            when (groupedItem[index]) {
              is UiScheduleItem.Header -> GridItemSpan(maxLineSpan)
              else -> GridItemSpan(1)
            }
          },
      ) { index ->
        when (val item = groupedItem[index]) {
          is UiScheduleItem.Header -> DateText(day = item.day, date = item.date, month = item.month)

          is UiScheduleItem.Content -> {
            val data = item.data
            MoviePoster(
                name = data.content.name,
                imageUrl = data.content.image,
                label = "${data.releaseType} • ${data.year}",
                slug = data.content.slug,
                onClick = {
                  navController.navigate(Routes.ContentScreen.route + "/${data.content.slug}")
                },
            )
          }
          null -> {}
        }
      }
      item(span = { GridItemSpan(maxLineSpan) }) {
        Row(horizontalArrangement = Arrangement.Center) {
          Text(text = "--- END ---", color = Color.Gray)
        }
      }
    }
  }
}
