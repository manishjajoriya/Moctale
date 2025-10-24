package com.manishjajoriya.moctale.presentation.scheduleScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.manishjajoriya.moctale.domain.model.schedule.Category
import com.manishjajoriya.moctale.domain.model.schedule.MovieCategory
import com.manishjajoriya.moctale.domain.model.schedule.SeriesCategory
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.model.schedule.UiScheduleItem
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.components.ErrorMessageText
import com.manishjajoriya.moctale.presentation.components.MoviePoster
import com.manishjajoriya.moctale.presentation.scheduleScreen.components.DateText
import com.manishjajoriya.moctale.presentation.scheduleScreen.components.FilterSelector
import com.manishjajoriya.moctale.presentation.scheduleScreen.components.TabSelector
import com.manishjajoriya.moctale.ui.theme.Pink
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun ScheduleScreen(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: ScheduleViewModel,
) {
  val scheduleData by viewModel.scheduleData.collectAsState()
  val scheduleDataPagingItem = scheduleData?.collectAsLazyPagingItems()
  val loadState = scheduleDataPagingItem?.loadState
  var loadStateLoading by remember { mutableStateOf(false) }
  val loading by viewModel.loading.collectAsState()
  val isRefreshing by viewModel.isRefreshing.collectAsState()
  val error by viewModel.error.collectAsState()
  var loadStateError by remember { mutableStateOf<String?>(null) }

  // Filter
  var selectedTimeFilter by remember { mutableStateOf(TimeFilter.TODAY) }
  var selectedCategory by remember { mutableStateOf(Category.ALL) }
  var selectedMovieCategory by remember { mutableStateOf(MovieCategory.IN_THEATRES) }
  var selectedSeriesCategory by remember { mutableStateOf(SeriesCategory.NEW_SHOW) }

  LaunchedEffect(
      selectedTimeFilter,
      selectedCategory,
      selectedMovieCategory,
      selectedSeriesCategory,
  ) {
    when (selectedCategory) {
      Category.MOVIE -> viewModel.fetchScheduleData(selectedTimeFilter, selectedMovieCategory.value)
      Category.SERIES ->
          viewModel.fetchScheduleData(selectedTimeFilter, selectedSeriesCategory.value)
      else -> viewModel.fetchScheduleData(selectedTimeFilter, null)
    }
  }

  LaunchedEffect(loadState) {
    loadState?.let {
      when (loadState.refresh) {
        is LoadState.Error -> {
          loadStateError = (loadState.refresh as LoadState.Error).error.message
          loadStateLoading = false
        }
        LoadState.Loading -> loadStateLoading = true
        is LoadState.NotLoading -> loadStateLoading = false
      }
    }
  }

  PullToRefreshBox(
      isRefreshing = isRefreshing,
      onRefresh = {
        when (selectedCategory) {
          Category.MOVIE ->
              viewModel.fetchScheduleData(
                  selectedTimeFilter,
                  selectedMovieCategory.value,
                  isRefreshCall = true,
              )
          Category.SERIES ->
              viewModel.fetchScheduleData(
                  selectedTimeFilter,
                  selectedSeriesCategory.value,
                  isRefreshCall = true,
              )
          else -> viewModel.fetchScheduleData(selectedTimeFilter, null, isRefreshCall = true)
        }
      },
      modifier = Modifier.padding(paddingValues),
  ) {
    Column(modifier = Modifier.padding(horizontal = 12.dp).fillMaxSize()) {
      if (scheduleDataPagingItem != null && scheduleDataPagingItem.itemCount != 0) {

        // Time Filter
        TabSelector(selectedTimeFilter) { timeFilter ->
          selectedTimeFilter = timeFilter
          selectedCategory = Category.ALL
          selectedMovieCategory = MovieCategory.IN_THEATRES
          selectedSeriesCategory = SeriesCategory.NEW_SHOW
        }

        // Category
        if (selectedCategory == Category.ALL) {
          FilterSelector(
              fields = Category.entries,
              selectedFiled = selectedCategory,
              onClear = {},
          ) { category ->
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
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          items(
              scheduleDataPagingItem.itemCount,
              span = { index ->
                when (scheduleDataPagingItem[index]) {
                  is UiScheduleItem.Header -> GridItemSpan(maxLineSpan)
                  else -> GridItemSpan(1)
                }
              },
          ) { index ->
            when (val item = scheduleDataPagingItem[index]) {
              is UiScheduleItem.Header ->
                  DateText(day = item.day, date = item.date, month = item.month)

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
            Text(
                text = "--- END ---",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Typography.titleMedium.copy(fontSize = 20.sp),
                color = Color.Gray,
            )
          }
        }
      } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
          item {
            if (loadStateLoading || loading) CircularProgressIndicator(color = Pink)
            loadStateError?.let { error ->
              ErrorMessageText(text = error, isPullToRefreshAvailable = true)
            }
            error?.let { error ->
              loadStateError = null
              ErrorMessageText(text = error, isPullToRefreshAvailable = true)
            }
          }
        }
      }
    }
  }
}
