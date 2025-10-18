package com.manishjajoriya.moctale.presentation.scheduleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.model.schedule.UiScheduleItem
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val moctaleRepository: MoctaleRepository) :
    ViewModel() {

  fun getGroupedScheduleFlow(
      timeFilter: TimeFilter,
      filterName: String? = null,
  ): Flow<PagingData<UiScheduleItem>> {
    return moctaleRepository.getGroupedScheduleData(timeFilter, filterName).cachedIn(viewModelScope)
  }
}
