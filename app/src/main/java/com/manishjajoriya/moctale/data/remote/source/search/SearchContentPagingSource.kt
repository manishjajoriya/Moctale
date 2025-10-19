package com.manishjajoriya.moctale.data.remote.source.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manishjajoriya.moctale.domain.model.search.content.Data
import com.manishjajoriya.moctale.domain.usecase.SearchUseCase

class SearchContentPagingSource(
    private val searchUseCase: SearchUseCase,
    private val searchTerm: String,
) : PagingSource<Int, Data>() {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
    return try {
      val currentPage = params.key ?: 1
      val response = searchUseCase.searchContent(searchTerm, currentPage)
      LoadResult.Page(
          data = response.data,
          prevKey = response.previousPage,
          nextKey = if (currentPage >= response.totalPages) null else response.nextPage,
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
