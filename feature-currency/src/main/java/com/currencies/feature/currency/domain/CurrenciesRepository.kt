package com.currencies.feature.currency.domain

import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    fun getCurrencies(): Flow<List<Currency>>

    suspend fun updateCurrencies(): Result<Unit>

    fun refreshCurrenciesPeriodically()
}