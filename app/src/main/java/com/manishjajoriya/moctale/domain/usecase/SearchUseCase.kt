package com.manishjajoriya.moctale.domain.usecase

import com.manishjajoriya.moctale.data.remote.api.MoctaleApi
import com.manishjajoriya.moctale.domain.model.search.content.SearchContent
import com.manishjajoriya.moctale.domain.model.search.person.SearchPerson
import com.manishjajoriya.moctale.domain.model.search.user.SearchUser
import jakarta.inject.Inject

class SearchUseCase @Inject constructor(private val moctaleApi: MoctaleApi) {

  suspend fun searchContent(searchTerm: String, page: Int): SearchContent {
    return moctaleApi.searchContent(searchTerm, page)
  }

  suspend fun searchPerson(searchTerm: String, page: Int): SearchPerson {
    return moctaleApi.searchPerson(searchTerm, page)
  }

  suspend fun searchUser(searchTerm: String, page: Int): SearchUser {
    return moctaleApi.searchUser(searchTerm, page)
  }
}
