package com.currencies.domain

import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    fun getCurrencies(): Flow<List<Currency>>

    suspend fun updateCurrencies()
}