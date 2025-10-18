package com.manishjajoriya.moctale.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manishjajoriya.moctale.domain.model.person.Data
import com.manishjajoriya.moctale.domain.usecase.PersonUseCase

class PersonContentPagingSource(
    private val personUseCase: PersonUseCase,
    private val name: String,
) : PagingSource<Int, Data>() {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
    return try {
      val currentPage = params.key ?: 1
      val response = personUseCase.personContent(name = name, page = currentPage)

      LoadResult.Page(
          data = response.data,
          prevKey = response.previousPage,
          nextKey = if (response.currentPage >= response.totalPages) null else response.nextPage,
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
