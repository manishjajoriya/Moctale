package com.manishjajoriya.moctale.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
  suspend fun setAuthToken(token: String)

  fun getAuthToken(): Flow<String?>

  suspend fun clearAuthTone()

  suspend fun setUsername(username: String)

  fun getUsername(): Flow<String?>

  suspend fun clearUsername()
}
