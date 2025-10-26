package com.manishjajoriya.moctale.presentation.exploreScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.moctale.Constants
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.domain.model.explore.ExploreItem
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ExploreViewModel
@Inject
constructor(
    private val moctaleApiUseCase: MoctaleApiUseCase,
    private val networkStatusManager: NetworkStatusManager,
) : ViewModel() {

  private val _exploreData = MutableStateFlow<List<ExploreItem>?>(null)
  val exploreData = _exploreData.asStateFlow()

  private val _hasExploreDataFetched = MutableStateFlow(false)
  val hasExploreDataFetched = _hasExploreDataFetched.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

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
        if (isRefreshCall) _isRefreshing.value = true else _loading.value = true
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
        _loading.value = false
        _isRefreshing.value = false
      }
    }
  }

  fun fetchExploreData(isRefreshCall: Boolean = false) {
    callApi(value = _exploreData, isRefreshCall = isRefreshCall) {
      moctaleApiUseCase.exploreUseCase()
    }
  }

  fun setHasExploreDataFetched(new: Boolean) {
    _hasExploreDataFetched.value = new
  }
}
