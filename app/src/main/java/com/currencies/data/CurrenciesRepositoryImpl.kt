package com.currencies.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.currencies.data.local.LocalStorage
import com.currencies.data.remote.RemoteRequestFrequencyChecker
import com.currencies.data.remote.RemoteStorage
import com.currencies.data.worker.RefreshCurrenciesWorker
import com.currencies.domain.CurrenciesRepository
import com.currencies.domain.Currency
import com.currencies.domain.exception.NoInternetConnectionException
import kotlinx.coroutines.flow.Flow

class CurrenciesRepositoryImpl(
    private val remoteStorage: RemoteStorage,
    private val localStorage: LocalStorage,
    private val requestFrequencyChecker: RemoteRequestFrequencyChecker,
    private val context: Context
) : CurrenciesRepository {

    override fun getCurrencies(): Flow<List<Currency>> = localStorage.getCurrencies()

    override suspend fun updateCurrencies() {
        val requestForbidden = requestFrequencyChecker.isRequestForbidden()
        if (requestForbidden.not()) {
            if (!isOnline()) {
                throw NoInternetConnectionException()
            }
            val currencies = remoteStorage.fetchCurrencies()
            requestFrequencyChecker.requestDone()
            localStorage.replaceCurrencies(currencies)
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

    override fun refreshCurrenciesPeriodically() {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            RefreshCurrenciesWorker.NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            RefreshCurrenciesWorker.makeRequest()
        )
    }
}