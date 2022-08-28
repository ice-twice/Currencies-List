package com.currencies.feature.currency.di.data

import com.currencies.feature.currency.data.local.CurrencyDatabase
import com.currencies.feature.currency.data.local.CurrencyEntityMapper
import com.currencies.feature.currency.data.local.LocalStorage
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