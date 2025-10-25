package com.manishjajoriya.moctale.presentation.scheduleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manishjajoriya.moctale.Constants
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.model.schedule.UiScheduleItem
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ScheduleViewModel
@Inject
constructor(
    private val moctaleRepository: MoctaleRepository,
    private val networkStatusManager: NetworkStatusManager,
) : ViewModel() {

  private val _scheduleData = MutableStateFlow<Flow<PagingData<UiScheduleItem>>?>(null)
  val scheduleData = _scheduleData.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  fun <T : Any> callApi(
      value: MutableStateFlow<T?>,
      isRefreshCall: Boolean,
      fn: suspend () -> T,
  ) {
    viewModelScope.launch {
      try {
        if (isRefreshCall) _isRefreshing.value = true else _loading.value = true
        _error.value = null
        value.value = null

        if (!networkStatusManager.isConnected()) {
          delay(500)
          _error.value = Constants.ERROR_MESSAGE
          return@launch
        }
        val result = withContext(Dispatchers.IO) { fn() }
        value.value = result
      } catch (e: Exception) {
        _error.value = e.message ?: "Unknown error"
      } finally {
        _loading.value = false
        _isRefreshing.value = false
      }
    }
  }

  fun fetchScheduleData(
      timeFilter: TimeFilter,
      filterName: String? = null,
      isRefreshCall: Boolean = false,
  ) {
    callApi(value = _scheduleData, isRefreshCall = isRefreshCall) {
      moctaleRepository.getGroupedScheduleData(timeFilter, filterName).cachedIn(viewModelScope)
    }
  }
}
