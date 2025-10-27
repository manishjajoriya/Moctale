package com.manishjajoriya.moctale.data.remote.source.schedule

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manishjajoriya.moctale.domain.model.schedule.Data
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.usecase.ScheduleUseCase

class SchedulePagingSource(
    private val scheduleUseCase: ScheduleUseCase,
    private val timeFilter: TimeFilter,
    private val releaseType: String? = null,
) : PagingSource<Int, Data>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
    return try {
      val currentPage = params.key ?: 1
      val response =
          scheduleUseCase(timeFilter = timeFilter, page = currentPage, releaseType = releaseType)

      LoadResult.Page(
          data = response.data,
          prevKey = response.previousPage,
          nextKey = response.nextPage,
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
          ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }
}
