package com.manishjajoriya.moctale.data.remote.source.browse

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manishjajoriya.moctale.domain.model.browse.Data
import com.manishjajoriya.moctale.domain.usecase.BrowseUseCase

class BrowsePagingSource(
  private val browseUseCase: BrowseUseCase,
  private val browseScreen : String,
  private val category: String?,
) : PagingSource<Int, Data>() {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
    return try {
      val currentPage = params.key ?: 1
      val response = browseUseCase.categoryData(browseScreen = browseScreen,category = category,page = currentPage)
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