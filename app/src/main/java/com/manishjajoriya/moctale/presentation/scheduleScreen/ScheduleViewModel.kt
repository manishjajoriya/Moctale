package com.manishjajoriya.moctale.presentation.scheduleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.model.schedule.UiScheduleItem
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@HiltViewModel
class ScheduleViewModel
@Inject
constructor(
    private val moctaleRepository: MoctaleRepository,
    private val networkStatusManager: NetworkStatusManager,
) : ViewModel() {

  private val _scheduleData = MutableStateFlow<Flow<PagingData<UiScheduleItem>>?>(null)
  val scheduleData = _scheduleData.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  fun fetchScheduleData(
      timeFilter: TimeFilter,
      filterName: String? = null,
      isRefreshCall: Boolean = false,
  ) {
    if (!networkStatusManager.isConnected()) {
      _error.value = "No network available\nPlease check your network status"
      return
    }
    if (isRefreshCall) _isRefreshing.value = true

    viewModelScope.launch(Dispatchers.IO) {
      moctaleRepository
          .getGroupedScheduleData(timeFilter, filterName)
          .cachedIn(viewModelScope)
          .collect { pagingData ->
            _scheduleData.value = flowOf(pagingData)
            _error.value = null
            _isRefreshing.value = false
          }
    }
  }
}
