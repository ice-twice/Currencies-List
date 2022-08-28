package com.currencies.feature.currency.domain

class RefreshCurrenciesPeriodicallyUseCase(private val currenciesRepository: CurrenciesRepository) {

    operator fun invoke() = currenciesRepository.refreshCurrenciesPeriodically()
}