package com.manishjajoriya.moctale.domain.repository

import androidx.paging.PagingData
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.model.schedule.UiScheduleItem
import kotlinx.coroutines.flow.Flow

interface MoctaleRepository {

  fun getGroupedScheduleData(
      timeFilter: TimeFilter,
      releaseType: String?,
  ): Flow<PagingData<UiScheduleItem>>

  fun getPersonContentData(name : String): Flow<PagingData<com.manishjajoriya.moctale.domain.model.person.Data>>
}
