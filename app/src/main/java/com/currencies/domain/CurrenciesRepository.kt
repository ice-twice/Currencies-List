package com.currencies.domain

import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    fun fetchCurrencies(refresh: Boolean = false): Flow<List<Currency>>
}