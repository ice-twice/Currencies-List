package com.test.currencies.domain

import com.test.currencies.data.CurrenciesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn

class FetchCurrenciesInteractor(
    private val currenciesRepository: CurrenciesRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    fun fetchCurrencies() = currenciesRepository.fetchCurrencies()
        .flowOn(coroutineDispatcher)
}