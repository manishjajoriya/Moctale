package com.manishjajoriya.moctale.presentation.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manishjajoriya.moctale.Constants
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.domain.model.search.content.Data
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val moctaleRepository: MoctaleRepository,
    private val networkStatusManager: NetworkStatusManager,
) : ViewModel() {

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

  fun <T : Any> callApi(
      value: MutableStateFlow<T?>,
      isRefreshCall: Boolean,
      fn: suspend () -> T,
  ) {
    viewModelScope.launch {
      try {
        if (isRefreshCall) _isRefreshing.value = true
        _error.value = null
        value.value = null

        if (!networkStatusManager.isConnected()) {
          delay(500)
          _error.value = Constants.ERROR_MESSAGE
          return@launch
        }
        val result = withContext(Dispatchers.IO) { fn() }
        value.value = result
      } catch (e: Exception) {
        _error.value = e.message ?: "Unknown error"
      } finally {
        _isRefreshing.value = false
      }
    }
  }

  fun getSearchContentFlow(searchTerm: String, isRefreshCall: Boolean = false) {
    callApi(value = _searchContentData, isRefreshCall = isRefreshCall) {
      moctaleRepository.getSearchContentData(searchTerm).cachedIn(viewModelScope)
    }
  }

  fun getSearchPersonFlow(searchTerm: String, isRefreshCall: Boolean = false) {
    callApi(value = _searchPersonData, isRefreshCall = isRefreshCall) {
      moctaleRepository.getSearchPersonData(searchTerm).cachedIn(viewModelScope)
    }
  }

  fun getSearchUserFlow(searchTerm: String, isRefreshCall: Boolean = false) {
    callApi(value = _searchUserData, isRefreshCall = isRefreshCall) {
      moctaleRepository.getSearchUserData(searchTerm).cachedIn(viewModelScope)
    }
  }
}
