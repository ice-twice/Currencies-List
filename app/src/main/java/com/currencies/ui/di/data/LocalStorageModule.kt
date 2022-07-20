package com.currencies.ui.di.data

import com.currencies.data.local.CurrencyDatabase
import com.currencies.data.local.CurrencyEntityMapper
import com.currencies.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Singleton
    @Provides
    fun provide(currencyDatabase: CurrencyDatabase, currencyEntityMapper: CurrencyEntityMapper) =
        LocalStorage(currencyDatabase.currencyDao(), currencyEntityMapper)
}