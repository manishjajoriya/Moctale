package com.manishjajoriya.moctale.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.manishjajoriya.moctale.data.remote.source.SchedulePagingSource
import com.manishjajoriya.moctale.domain.model.schedule.Data
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.model.schedule.UiScheduleItem
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoctaleRepositoryImpl @Inject constructor(private val moctaleApiUseCase: MoctaleApiUseCase) :
    MoctaleRepository {
  fun getScheduleData(
      timeFilter: TimeFilter,
      releaseType: String?,
  ): Flow<PagingData<Data>> {
    return Pager(
            config =
                PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 5,
                    enablePlaceholders = false,
                    initialLoadSize = 20,
                ),
            pagingSourceFactory = {
              SchedulePagingSource(
                  scheduleUseCase = moctaleApiUseCase.scheduleUseCase,
                  timeFilter = timeFilter,
                  releaseType = releaseType,
              )
            },
        )
        .flow
  }

  override fun getGroupedScheduleData(
      timeFilter: TimeFilter,
      releaseType: String?,
  ): Flow<PagingData<UiScheduleItem>> {
    val baseFlow = getScheduleData(timeFilter, releaseType)

    return baseFlow.map { pagingData ->
      val contentPaging = pagingData.map { UiScheduleItem.Content(it) }

      if (timeFilter == TimeFilter.TODAY) {
        contentPaging.insertSeparators { before, after ->
          if (before == null && after != null) {
            UiScheduleItem.Header(
                day = after.data.dayOfWeek,
                date = after.data.day,
                month = after.data.month,
            )
          } else null
        }
      } else {
        contentPaging.insertSeparators { before, after ->
          when {
            before == null && after != null ->
                UiScheduleItem.Header(
                    day = after.data.dayOfWeek,
                    date = after.data.day,
                    month = after.data.month,
                )
            before != null && after != null && before.data.day != after.data.day ->
                UiScheduleItem.Header(
                    day = after.data.dayOfWeek,
                    date = after.data.day,
                    month = after.data.month,
                )
            else -> null
          }
        }
      }
    }
  }
}
