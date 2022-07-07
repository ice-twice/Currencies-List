package com.currencies.ui.di

import com.currencies.data.local.CurrencyDatabase
import com.currencies.data.local.CurrencyEntityMapper
import com.currencies.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class LocalStorageModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(currencyDatabase: CurrencyDatabase, currencyEntityMapper: CurrencyEntityMapper) =
        LocalStorage(currencyDatabase.currencyDao(), currencyEntityMapper)
}