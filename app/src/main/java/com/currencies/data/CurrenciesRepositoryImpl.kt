package com.currencies.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.currencies.data.local.LocalStorage
import com.currencies.domain.CurrenciesRepository
import com.currencies.domain.exception.NoInternetConnectionException
import kotlinx.coroutines.flow.flow

class CurrenciesRepositoryImpl(
    private val remoteStorage: RemoteStorage,
    private val localStorage: LocalStorage,
    private val requestFrequencyChecker: RemoteRequestFrequencyChecker,
    private val context: Context
) : CurrenciesRepository {

    override fun fetchCurrencies(refresh: Boolean) = flow {
        val requestForbidden = requestFrequencyChecker.isRequestForbidden()
        if (refresh && requestForbidden || !refresh) {
            val currencies = localStorage.fetchCurrencies()
            emit(currencies)
        }

        if (!requestForbidden) {
            if (!isOnline()) {
                throw NoInternetConnectionException()
            }
            val currencies = remoteStorage.fetchCurrencies()
            requestFrequencyChecker.requestDone()
            localStorage.replaceCurrencies(currencies)
            emit(currencies)
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
}