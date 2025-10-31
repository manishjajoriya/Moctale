package com.manishjajoriya.moctale.data.manager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesManager() {
  private val _authToken = MutableStateFlow<String?>(null)
  val authToken = _authToken.asStateFlow()

  private val _username = MutableStateFlow<String?>(null)
  val username = _username.asStateFlow()

  fun setToken(newToken: String?) {
    _authToken.value = newToken
  }

  fun setUsername(newUsername: String?) {
    _username.value = newUsername
  }
}
