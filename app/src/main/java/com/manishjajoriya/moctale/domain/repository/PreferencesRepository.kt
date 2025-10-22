package com.manishjajoriya.moctale.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
  suspend fun saveAuthToken(token: String)

  fun getAuthToken(): Flow<String?>
}
