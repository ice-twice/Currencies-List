package com.currencies.ui.di

import android.content.Context
import com.currencies.data.CurrenciesRepositoryImpl
import com.currencies.data.RemoteRequestFrequencyChecker
import com.currencies.data.RemoteStorage
import com.currencies.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class CurrenciesRepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(
        remoteStorage: RemoteStorage,
        remoteRequestFrequencyChecker: RemoteRequestFrequencyChecker,
        localStorage: LocalStorage,
        @ApplicationContext context: Context
    ) = CurrenciesRepositoryImpl(
        remoteStorage,
        localStorage,
        remoteRequestFrequencyChecker,
        context
    )
}