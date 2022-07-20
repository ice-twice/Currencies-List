package com.currencies.domain

class RefreshCurrenciesPeriodicallyUseCase(private val currenciesRepository: CurrenciesRepository) {

    operator fun invoke() = currenciesRepository.refreshCurrenciesPeriodically()
}