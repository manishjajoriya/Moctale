package com.manishjajoriya.moctale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.data.manager.PreferencesManager
import com.manishjajoriya.moctale.domain.model.identity.IdentityUserProfile
import com.manishjajoriya.moctale.domain.repository.PreferencesRepository
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val preferencesRepository: PreferencesRepository,
    private val preferencesManager: PreferencesManager,
    private val moctaleApiUseCase: MoctaleApiUseCase,
    private val networkStatusManager: NetworkStatusManager,
) : ViewModel() {

  private val _isLogin = MutableStateFlow<Boolean?>(null)
  val isLogin = _isLogin.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  init {
    fetchTokenFromLocalStorage()
  }

  fun saveAuthToken(token: String) {
    if (!networkStatusManager.isConnected()) {
      _error.value = "NO INTERNET"
      return
    }
    val cleanToken = token.trim().replace(Regex("[\\r\\n]"), "")
    viewModelScope.launch(Dispatchers.IO) {
      _loading.value = true
      preferencesManager.setToken(cleanToken)
      val identityUserProfile = getUserProfile()
      if (identityUserProfile != null) {
        _isLogin.value = true
        preferencesRepository.setAuthToken(cleanToken)
        preferencesManager.setUsername(identityUserProfile.username)
        preferencesRepository.setUsername(identityUserProfile.username)
      } else {
        _error.value =
            "Invalid token, please recheck your token and remove any new line and whitespace"
        preferencesManager.setToken(null)
      }
      _loading.value = false
    }
  }

  fun fetchTokenFromLocalStorage() {
    viewModelScope.launch(Dispatchers.IO) {
      _loading.value = true

      val savedToken = preferencesRepository.getAuthToken().firstOrNull()
      val savedUsername = preferencesRepository.getUsername().firstOrNull()

      if (savedToken.isNullOrEmpty() || savedUsername.isNullOrEmpty()) {
        _isLogin.value = false
        _loading.value = false
        return@launch
      }
      preferencesManager.setToken(savedToken)
      preferencesManager.setUsername(savedUsername)
      _isLogin.value = true
      _loading.value = false
    }
  }

  private suspend fun getUserProfile(): IdentityUserProfile? {
    return try {
      moctaleApiUseCase.identityUseCase.identityUserProfile()
    } catch (_: Exception) {
      null
    }
  }

  fun logout() {
    viewModelScope.launch(Dispatchers.IO) {
      preferencesRepository.clearAuthTone()
      preferencesRepository.clearUsername()
      preferencesManager.setToken(null)
      preferencesManager.setUsername(null)
      _isLogin.value = false
    }
  }
}
