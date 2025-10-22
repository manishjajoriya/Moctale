package com.manishjajoriya.moctale.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.manishjajoriya.moctale.Constants
import com.manishjajoriya.moctale.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataStoreImpl(private val context: Context) : PreferencesRepository {

  private val Context.dataStore: DataStore<Preferences> by
      preferencesDataStore(name = Constants.PREFERENCES_NAME)

  private val AUTH_TOKEN_KEY = stringPreferencesKey(Constants.AUTH_TOKEN)

  override suspend fun saveAuthToken(token: String) {
    context.dataStore.edit { pref -> pref[AUTH_TOKEN_KEY] = token }
  }

  override fun getAuthToken(): Flow<String?> {
    return context.dataStore.data.map { preferences -> preferences[AUTH_TOKEN_KEY] }
  }
}
