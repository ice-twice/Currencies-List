package com.currencies

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import androidx.fragment.app.strictmode.FragmentStrictMode
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.currencies.feature.currency.domain.RefreshCurrenciesPeriodicallyUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

//  TODO share build logic between modules https://docs.gradle.org/current/userguide/platforms.html Version Catalog

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    // TODO put into feature-currency using https://developer.android.com/topic/libraries/app-startup ?
    @Inject
    lateinit var refreshCurrenciesPeriodicallyUseCase: RefreshCurrenciesPeriodicallyUseCase

    override fun onCreate() {
        setupStrictMode()
        super.onCreate()
        refreshCurrenciesPeriodicallyUseCase()
    }

    private fun setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            FragmentStrictMode.defaultPolicy = FragmentStrictMode.Policy.Builder()
                .detectFragmentReuse()
                .detectFragmentTagUsage()
                .detectRetainInstanceUsage()
                .detectSetUserVisibleHint()
                .detectTargetFragmentUsage()
                .detectWrongFragmentContainer()
                .penaltyLog()
                .build()
        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}