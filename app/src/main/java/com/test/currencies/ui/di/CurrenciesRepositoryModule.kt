package com.test.currencies.ui.di

import com.test.currencies.data.CurrenciesRepository
import com.test.currencies.data.RemoteRequestFrequencyChecker
import com.test.currencies.data.RemoteStorage
import com.test.currencies.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class CurrenciesRepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(
        remoteStorage: RemoteStorage,
        remoteRequestFrequencyChecker: RemoteRequestFrequencyChecker,
        localStorage: LocalStorage
    ): CurrenciesRepository {
        return CurrenciesRepository(
            remoteStorage,
            localStorage,
            remoteRequestFrequencyChecker
        )
    }
}