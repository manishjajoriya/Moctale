package com.manishjajoriya.moctale.domain.usecase

import com.manishjajoriya.moctale.data.remote.api.MoctaleApi
import com.manishjajoriya.moctale.domain.model.schedule.Schedule
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import jakarta.inject.Inject

class ScheduleUseCase @Inject constructor(private val moctaleApi: MoctaleApi) {
  suspend operator fun invoke(
      timeFilter: TimeFilter,
      page: Int,
      releaseType: String?,
  ): Schedule {
    return moctaleApi.schedule(timeFilter.value, page, releaseType)
  }
}
