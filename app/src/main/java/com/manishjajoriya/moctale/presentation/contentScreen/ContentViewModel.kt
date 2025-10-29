package com.manishjajoriya.moctale.presentation.contentScreen

import androidx.lifecycle.viewModelScope
import com.manishjajoriya.moctale.core.base.BaseViewModel
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.domain.model.content.Content
import com.manishjajoriya.moctale.domain.model.content.info.ContentInfo
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ContentViewModel
@Inject
constructor(
    private val moctaleApiUseCase: MoctaleApiUseCase,
    networkStatusManager: NetworkStatusManager,
) : BaseViewModel(networkStatusManager) {

  private val _content = MutableStateFlow<Content?>(null)
  val content = _content.asStateFlow()

  private val _contentInfo = MutableStateFlow<ContentInfo?>(null)
  val contentInfo = _contentInfo.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  fun fetchContent(slug: String, isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _content.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = { _loading.value = it },
        onError = { _error.value = it },
    ) {
      moctaleApiUseCase.contentUseCase(slug = slug)
    }
  }

  fun fetchContentInfo(slug: String, isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _contentInfo.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = {_isRefreshing.value = it},
        onLoading = { _loading.value = it },
        onError = { _error.value = it },
    ) {
      moctaleApiUseCase.activityUseCase.contentInfo(slug = slug)
    }
  }

  fun switchWatchedStatus(slug: String, isWatched: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
      if (isWatched) moctaleApiUseCase.activityUseCase.deleteMarkAsWatched(slug)
      else moctaleApiUseCase.activityUseCase.markAsWatched(slug)
      fetchContentInfo(slug)
    }
  }
}
