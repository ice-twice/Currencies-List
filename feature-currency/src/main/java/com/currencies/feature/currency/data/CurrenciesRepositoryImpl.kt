package com.currencies.feature.currency.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.currencies.feature.currency.data.local.LocalStorage
import com.currencies.feature.currency.data.remote.RemoteRequestFrequencyChecker
import com.currencies.feature.currency.data.remote.RemoteStorage
import com.currencies.feature.currency.data.worker.RefreshCurrenciesWorker
import com.currencies.feature.currency.domain.CurrenciesRepository
import com.currencies.feature.currency.domain.Currency
import com.currencies.feature.currency.domain.exception.NoInternetConnectionException
import com.currencies.feature.currency.domain.extensions.foldSuccess
import kotlinx.coroutines.flow.Flow

class CurrenciesRepositoryImpl(
    private val remoteStorage: RemoteStorage,
    private val localStorage: LocalStorage,
    private val requestFrequencyChecker: RemoteRequestFrequencyChecker,
    private val context: Context
) : CurrenciesRepository {

    override fun getCurrencies(): Flow<List<Currency>> = localStorage.getCurrencies()

    override suspend fun updateCurrencies(): Result<Unit> {
        val requestForbidden = requestFrequencyChecker.isRequestForbidden()
        return if (requestForbidden) {
            Result.success(Unit)
        } else {
            if (isOnline().not()) {
                Result.failure(NoInternetConnectionException())
            } else {
                remoteStorage.fetchCurrencies().foldSuccess(
                    onSuccess = { currencies ->
                        requestFrequencyChecker.requestDone()
                        localStorage.replaceCurrencies(currencies)
                        Unit
                    }
                )
            }
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