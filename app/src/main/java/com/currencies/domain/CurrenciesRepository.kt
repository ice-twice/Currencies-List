package com.currencies.domain

import com.currencies.domain.exception.NoInternetConnectionException
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    fun getCurrencies(): Flow<List<Currency>>

    /**
     * @throws NoInternetConnectionException in case there is no internet.
     */
    suspend fun updateCurrencies()

    fun refreshCurrenciesPeriodically()
}