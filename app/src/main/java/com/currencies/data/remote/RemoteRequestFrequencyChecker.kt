package com.currencies.data.remote

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

class RemoteRequestFrequencyChecker(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(PREFERENCES_FILE_NAME)

    suspend fun isRequestForbidden(): Boolean {
        val key = createKey()
        val lastRequestTime = context.dataStore.data.first()[key] ?: 0
        val currentTime = System.currentTimeMillis()
        return currentTime - lastRequestTime < MILLISECONDS_BETWEEN_REQUEST
    }

    private fun createKey() = longPreferencesKey(KEY_LAST_SUCCESS_REQUEST_TIME)

    suspend fun requestDone() {
        val key = createKey()
        context.dataStore.edit { preferences -> preferences[key] = System.currentTimeMillis() }
    }

    private companion object {
        private const val PREFERENCES_FILE_NAME = "currencies_request_settings"
        private const val KEY_LAST_SUCCESS_REQUEST_TIME = "last_success_request_time"
        private val MILLISECONDS_BETWEEN_REQUEST = TimeUnit.SECONDS.toMillis(20)
    }
}