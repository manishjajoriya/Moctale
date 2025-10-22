package com.manishjajoriya.moctale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.moctale.data.manager.PreferencesManager
import com.manishjajoriya.moctale.domain.repository.PreferencesRepository
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val preferencesRepository: PreferencesRepository,
    private val preferencesManager: PreferencesManager,
    private val moctaleApiUseCase: MoctaleApiUseCase,
) : ViewModel() {

  private val _isLogin = MutableStateFlow(false)
  val isLogin = _isLogin.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  init {
    fetchTokenStore()
  }

  fun saveAuthToken(token: String) {
    val cleanToken = token.trim().replace(Regex("[\\r\\n]"), "")
    viewModelScope.launch {
      _loading.value = true
      withContext(Dispatchers.IO) { preferencesRepository.saveAuthToken(cleanToken) }
      preferencesManager.setToken(cleanToken)
      _isLogin.value = validateToken()
      _loading.value = false
    }
  }

  fun fetchTokenStore() {
    viewModelScope.launch {
      _loading.value = true

      val token = preferencesManager.authToken.value
      if (!token.isNullOrEmpty()) {
        _isLogin.value = true
        _loading.value = false
        return@launch
      }

      val localToken =
          withContext(Dispatchers.IO) { preferencesRepository.getAuthToken().firstOrNull() }

      if (localToken.isNullOrEmpty()) {
        _loading.value = false
        return@launch
      }
      preferencesManager.setToken(localToken)
      val valid = validateToken()
      _isLogin.value = valid
      _loading.value = false
    }
  }

  private suspend fun validateToken(): Boolean {
    return try {
      moctaleApiUseCase.validateUseCase()
      true
    } catch (_: Exception) {
      false
    }
  }
}
