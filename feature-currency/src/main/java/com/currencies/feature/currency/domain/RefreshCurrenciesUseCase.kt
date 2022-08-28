package com.currencies.feature.currency.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RefreshCurrenciesUseCase(
    private val currenciesRepository: CurrenciesRepository,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() {
        withContext(backgroundDispatcher) {
            currenciesRepository.updateCurrencies()
        }
    }
}