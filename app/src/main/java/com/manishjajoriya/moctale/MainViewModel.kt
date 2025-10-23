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
    fetchTokenFromLocalStorage()
  }

  fun saveAuthToken(token: String) {
    val cleanToken = token.trim().replace(Regex("[\\r\\n]"), "")
    viewModelScope.launch {
      _loading.value = true
      preferencesManager.setToken(cleanToken)
      val isValid = validateToken()
      if (isValid) {
        _isLogin.value = true
        withContext(Dispatchers.IO) { preferencesRepository.setAuthToken(cleanToken) }
      }
      _loading.value = false
    }
  }

  fun fetchTokenFromLocalStorage() {
    viewModelScope.launch {
      _loading.value = true

      val savedToken =
          withContext(Dispatchers.IO) { preferencesRepository.getAuthToken().firstOrNull() }

      if (savedToken.isNullOrEmpty()) {
        _isLogin.value = false
        _loading.value = false
        return@launch
      }
      preferencesManager.setToken(savedToken)
      _isLogin.value = true
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
