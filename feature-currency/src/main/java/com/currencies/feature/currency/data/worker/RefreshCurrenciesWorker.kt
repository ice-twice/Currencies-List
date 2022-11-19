package com.currencies.feature.currency.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.currencies.feature.currency.domain.CurrenciesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class RefreshCurrenciesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val currenciesRepository: CurrenciesRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return currenciesRepository.updateCurrencies()
            .fold({ Result.success() }, { Result.failure() })
    }

    companion object {
        const val NAME = "RefreshCurrenciesWork"

        fun makeRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiresBatteryNotLow(true)
                .setRequiresDeviceIdle(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
            return PeriodicWorkRequestBuilder<RefreshCurrenciesWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
        }
    }
}