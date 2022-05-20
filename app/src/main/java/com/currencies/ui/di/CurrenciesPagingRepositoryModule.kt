package com.currencies.ui.di

import com.currencies.data.RemoteStorage
import com.currencies.data.paging.CurrenciesPagingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class CurrenciesPagingRepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(remoteStorage: RemoteStorage) = CurrenciesPagingRepository(remoteStorage)
}