package com.test.currencies.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import java.util.concurrent.TimeUnit

class RemoteRequestFrequencyChecker(private val context: Context) {

    fun isRequestForbidden(): Boolean {
        val lastRequestTime =
            getPreferences().getLong(KEY_LAST_SUCCESS_REQUEST_TIME, 0)
        val currentTime = System.currentTimeMillis()
        return currentTime - lastRequestTime < MILLISECONDS_BETWEEN_REQUEST
    }

    private fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    @SuppressLint("ApplySharedPref")
    fun requestDone() {
        getPreferences().edit()
            .putLong(KEY_LAST_SUCCESS_REQUEST_TIME, System.currentTimeMillis())
            .commit()
    }

    companion object {
        const val SHARED_PREFERENCES_NAME = "currencies_request_settings"
        const val KEY_LAST_SUCCESS_REQUEST_TIME = "last_success_request_time"
        var MILLISECONDS_BETWEEN_REQUEST = TimeUnit.SECONDS.toMillis(20)
    }
}