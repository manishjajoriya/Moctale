package com.manishjajoriya.moctale.presentation.scheduleScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manishjajoriya.moctale.core.base.BaseViewModel
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.domain.model.schedule.Category
import com.manishjajoriya.moctale.domain.model.schedule.MovieCategory
import com.manishjajoriya.moctale.domain.model.schedule.SeriesCategory
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.model.schedule.UiScheduleItem
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class ScheduleViewModel
@Inject
constructor(
    private val moctaleRepository: MoctaleRepository,
    networkStatusManager: NetworkStatusManager,
) : BaseViewModel(networkStatusManager) {

  private val _scheduleData = MutableStateFlow<Flow<PagingData<UiScheduleItem>>?>(null)
  val scheduleData = _scheduleData.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  // Private filter
  private val _selectedTimeFilter = MutableStateFlow(TimeFilter.TODAY)
  private val _selectedCategory = MutableStateFlow(Category.ALL)
  private val _selectedMovieCategory = MutableStateFlow(MovieCategory.IN_THEATRES)
  private val _selectedSeriesCategory = MutableStateFlow(SeriesCategory.NEW_SHOW)

  private val _filterName = MutableStateFlow<String?>(null)

  private val filterParams =
      combine(
          _selectedTimeFilter,
          _selectedCategory,
          _selectedMovieCategory,
          _selectedSeriesCategory,
      ) { time, category, movieCat, seriesCat ->
        FilterParams(time, category, movieCat, seriesCat)
      }

  private var lastParams: FilterParams? = null

  // Public access filter
  val selectedTimeFilter = _selectedTimeFilter.asStateFlow()
  val selectedCategory = _selectedCategory.asStateFlow()
  val selectedMovieCategory = _selectedMovieCategory.asStateFlow()
  val selectedSeriesCategory = _selectedSeriesCategory.asStateFlow()

  init {
    viewModelScope.launch {
      filterParams.collect { params ->
        if (params == lastParams) return@collect
        lastParams = params

        _filterName.value =
            when (params.category) {
              Category.MOVIE -> params.movieCategory.value
              Category.SERIES -> params.seriesCategory.value
              Category.ALL -> null
            }

        fetchScheduleData()
      }
    }
  }

  fun fetchScheduleData(
      isRefreshCall: Boolean = false,
  ) {
    callApi(
        onValue = { _scheduleData.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = { _loading.value = it },
        onError = { _error.value = it },
    ) {
      moctaleRepository
          .getGroupedScheduleData(_selectedTimeFilter.value, _filterName.value)
          .cachedIn(viewModelScope)
    }
  }

  // Filter update
  fun setSelectedTimeFilter(new: TimeFilter) {
    _selectedTimeFilter.value = new
  }

  fun setSelectedCategory(new: Category) {
    _selectedCategory.value = new
  }

  fun setSelectedMovieCategory(new: MovieCategory) {
    _selectedMovieCategory.value = new
  }

  fun setSelectedSeriesCategory(new: SeriesCategory) {
    _selectedSeriesCategory.value = new
  }

  fun resetAllFilterToDefault() {
    _selectedCategory.value = Category.ALL
    _selectedMovieCategory.value = MovieCategory.IN_THEATRES
    _selectedSeriesCategory.value = SeriesCategory.NEW_SHOW
  }
}

data class FilterParams(
    val timeFilter: TimeFilter,
    val category: Category,
    val movieCategory: MovieCategory,
    val seriesCategory: SeriesCategory,
)
