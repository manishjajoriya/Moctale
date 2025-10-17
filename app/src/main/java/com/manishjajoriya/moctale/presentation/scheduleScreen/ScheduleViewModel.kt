package com.manishjajoriya.moctale.presentation.scheduleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.moctale.domain.model.schedule.Schedule
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.usecase.ScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val scheduleUseCase: ScheduleUseCase) :
    ViewModel() {

  private val _schedule = MutableStateFlow<Schedule?>(null)
  val schedule = _schedule.asStateFlow()

  fun fetchSchedule(timeFilter: TimeFilter, filterName: String? = null) {
    var attempts = 0
    _schedule.value = null
    viewModelScope.launch(Dispatchers.IO) {
      while (attempts < 3 && _schedule.value == null) {
        attempts++
        try {
          _schedule.value = scheduleUseCase(timeFilter, releaseType = filterName)
        } catch (e: Exception) {
          e.printStackTrace()
          delay(500)
        }
      }
    }
  }
}
