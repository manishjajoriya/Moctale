package com.manishjajoriya.moctale.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.moctale.Constants
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel(private val networkStatusManager: NetworkStatusManager) : ViewModel() {

  fun <T : Any> callApi(
      onValue: (T?) -> Unit,
      isRefreshCall: Boolean,
      onRefresh: (Boolean) -> Unit,
      onLoading: (Boolean) -> Unit,
      onError: (String?) -> Unit,
      fn: suspend () -> T,
  ) {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        if (isRefreshCall) onRefresh(true) else onLoading(true)
        onError(null)
        onValue(null)

        if (!networkStatusManager.isConnected()) {
          delay(500)
          onError(Constants.ERROR_MESSAGE)
        } else {
          val result = withContext(Dispatchers.IO) { fn() }
          onValue(result)
        }
      } catch (e: Exception) {
        onError(e.message ?: "Unknown error")
      } finally {
        onLoading(false)
        onRefresh(false)
      }
    }
  }
}
