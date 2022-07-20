package com.currencies.ui.di.data

import android.content.Context
import com.currencies.data.CurrenciesRepositoryImpl
import com.currencies.data.RemoteRequestFrequencyChecker
import com.currencies.data.RemoteStorage
import com.currencies.data.local.LocalStorage
import com.currencies.domain.CurrenciesRepository
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