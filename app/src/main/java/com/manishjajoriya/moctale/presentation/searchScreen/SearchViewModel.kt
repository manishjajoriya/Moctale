package com.manishjajoriya.moctale.presentation.searchScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manishjajoriya.moctale.core.base.BaseViewModel
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.domain.model.search.content.Data
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val moctaleRepository: MoctaleRepository,
    networkStatusManager: NetworkStatusManager,
) : BaseViewModel(networkStatusManager) {

  private val _searchContentData = MutableStateFlow<Flow<PagingData<Data>>?>(null)
  val searchContentData = _searchContentData.asStateFlow()

  private val _searchPersonData =
      MutableStateFlow<
          Flow<PagingData<com.manishjajoriya.moctale.domain.model.search.person.Data>>?
      >(
          null
      )
  val searchPersonData = _searchPersonData.asStateFlow()

  private val _searchUserData =
      MutableStateFlow<Flow<PagingData<com.manishjajoriya.moctale.domain.model.search.user.Data>>?>(
          null
      )

  val searchUserData = _searchUserData.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  fun getSearchContentFlow(searchTerm: String, isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _searchContentData.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = {},
        onError = { _error.value = it },
    ) {
      moctaleRepository.getSearchContentData(searchTerm).cachedIn(viewModelScope)
    }
  }

  fun getSearchPersonFlow(searchTerm: String, isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _searchPersonData.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = {},
        onError = { _error.value = it },
    ) {
      moctaleRepository.getSearchPersonData(searchTerm).cachedIn(viewModelScope)
    }
  }

  fun getSearchUserFlow(searchTerm: String, isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _searchUserData.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = {},
        onError = { _error.value = it },
    ) {
      moctaleRepository.getSearchUserData(searchTerm).cachedIn(viewModelScope)
    }
  }
}
