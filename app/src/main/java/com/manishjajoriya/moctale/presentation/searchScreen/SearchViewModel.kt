package com.manishjajoriya.moctale.presentation.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manishjajoriya.moctale.domain.model.search.content.Data
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SearchViewModel @Inject constructor(private val moctaleRepository: MoctaleRepository) :
    ViewModel() {

  private val _searchContentFlow = MutableStateFlow<Flow<PagingData<Data>>?>(null)
  val searchContentFlow = _searchContentFlow.asStateFlow()

  private val _searchPersonFlow =
      MutableStateFlow<
          Flow<PagingData<com.manishjajoriya.moctale.domain.model.search.person.Data>>?
      >(
          null
      )
  val searchPersonFlow = _searchPersonFlow.asStateFlow()

  private val _searchUserFlow =
      MutableStateFlow<Flow<PagingData<com.manishjajoriya.moctale.domain.model.search.user.Data>>?>(
          null
      )

  val searchUserFlow = _searchUserFlow.asStateFlow()

  fun getSearchContentFlow(searchTerm: String) {
    _searchContentFlow.value =
        moctaleRepository.getSearchContentData(searchTerm).cachedIn(viewModelScope)
  }

  fun getSearchPersonFlow(searchTerm: String) {
    _searchPersonFlow.value =
        moctaleRepository.getSearchPersonData(searchTerm).cachedIn(viewModelScope)
  }

  fun getSearchUserFlow(searchTerm: String) {
    _searchUserFlow.value = moctaleRepository.getSearchUserData(searchTerm).cachedIn(viewModelScope)
  }
}
