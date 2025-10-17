package com.manishjajoriya.moctale.presentation.scheduleScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.manishjajoriya.moctale.domain.model.schedule.Category
import com.manishjajoriya.moctale.domain.model.schedule.MovieCategory
import com.manishjajoriya.moctale.domain.model.schedule.SeriesCategory
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.components.MoviePoster
import com.manishjajoriya.moctale.presentation.scheduleScreen.components.FilterSelector
import com.manishjajoriya.moctale.presentation.scheduleScreen.components.TabSelector
import com.manishjajoriya.moctale.ui.theme.Typography

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

  val schedule by viewModel.schedule.collectAsState()

  LaunchedEffect(
      selectedTimeFilter,
      selectedCategory,
      selectedMovieCategory,
      selectedSeriesCategory,
  ) {
    when (selectedCategory) {
      Category.MOVIE -> viewModel.fetchSchedule(selectedTimeFilter, selectedMovieCategory.value)
      Category.SERIES -> viewModel.fetchSchedule(selectedTimeFilter, selectedSeriesCategory.value)
      else -> viewModel.fetchSchedule(selectedTimeFilter, null)
    }
  }
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

    schedule?.let { schedule ->
      if (schedule.data.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Text(text = "No content scheduled")
          Text(text = "Check back later for updates!")
        }
      } else {
        val groudData = schedule.data.groupBy { it.day }
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          groudData.forEach { day, dataList ->
            item(span = { GridItemSpan(maxLineSpan) }) {
              Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
              ) {
                Text(text = dataList[0].dayOfWeek.uppercase(), style = Typography.labelLarge)
                Text(
                    text = dataList[0].day,
                    style = Typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    text = dataList[0].month.uppercase(),
                    style = Typography.labelSmall.copy(color = Color.Gray),
                )
              }
            }
            itemsIndexed(dataList) { index, data ->
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
          }
        }
      }
    }
  }
}
