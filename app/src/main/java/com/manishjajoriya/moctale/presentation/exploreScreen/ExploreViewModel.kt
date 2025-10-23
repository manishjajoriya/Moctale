package com.manishjajoriya.moctale.presentation.exploreScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import retrofit2.HttpException

@HiltViewModel
class ExploreViewModel
@Inject
constructor(
    private val moctaleApiUseCase: MoctaleApiUseCase,
    private val networkStatusManager: NetworkStatusManager,
) : ViewModel() {

  private val _exploreData = MutableStateFlow<List<ExploreItem>?>(null)
  val exploreData = _exploreData.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  fun fetchExploreData(isRefreshCall: Boolean = false) {
    if (!networkStatusManager.isConnected()) {
      _error.value = "No network available\nPlease check your network status"
      return
    }
    _exploreData.value = null
    _error.value = null
    if (isRefreshCall) _isRefreshing.value = true else _loading.value = true
    viewModelScope.launch(Dispatchers.IO) {
      try {
        delay(3000)
        _exploreData.value = moctaleApiUseCase.exploreUseCase()
      } catch (e: HttpException) {
        _error.value = e.message
      } catch (e: Exception) {
        _error.value = e.message
      } finally {
        _isRefreshing.value = false
        _loading.value = false
      }
    }
  }
}
