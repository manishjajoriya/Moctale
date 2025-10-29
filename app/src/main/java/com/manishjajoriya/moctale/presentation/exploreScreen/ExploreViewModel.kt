package com.manishjajoriya.moctale.presentation.exploreScreen

import com.manishjajoriya.moctale.core.base.BaseViewModel
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.domain.model.explore.ExploreItem
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ExploreViewModel
@Inject
constructor(
    private val moctaleApiUseCase: MoctaleApiUseCase,
    networkStatusManager: NetworkStatusManager,
) : BaseViewModel(networkStatusManager) {

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

  fun fetchExploreData(isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _exploreData.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = { _loading.value = it },
        onError = { _error.value = it },
    ) {
      moctaleApiUseCase.exploreUseCase()
    }
  }

  fun setHasExploreDataFetched(new: Boolean) {
    _hasExploreDataFetched.value = new
  }
}
