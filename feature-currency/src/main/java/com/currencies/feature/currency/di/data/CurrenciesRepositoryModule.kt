package com.currencies.feature.currency.di.data

import android.content.Context
import com.currencies.feature.currency.data.CurrenciesRepositoryImpl
import com.currencies.feature.currency.data.local.LocalStorage
import com.currencies.feature.currency.data.remote.RemoteRequestFrequencyChecker
import com.currencies.feature.currency.data.remote.RemoteStorage
import com.currencies.feature.currency.domain.CurrenciesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CurrenciesRepositoryModule {

    @Singleton
    @Provides
    fun provide(
        remoteStorage: RemoteStorage,
        remoteRequestFrequencyChecker: RemoteRequestFrequencyChecker,
        localStorage: LocalStorage,
        @ApplicationContext context: Context
    ): CurrenciesRepository = CurrenciesRepositoryImpl(
        remoteStorage,
        localStorage,
        remoteRequestFrequencyChecker,
        context
    )
}