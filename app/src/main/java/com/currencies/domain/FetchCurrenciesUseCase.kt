package com.currencies.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn

class FetchCurrenciesUseCase(
    private val currenciesRepository: CurrenciesRepository,
    private val dispatcherIO: CoroutineDispatcher
) {

    fun fetchCurrencies(refresh: Boolean = false) = currenciesRepository.fetchCurrencies(refresh)
        .flowOn(dispatcherIO)
}