package com.currencies.feature.currency.domain

class GetCurrenciesUseCase(
    private val currenciesRepository: CurrenciesRepository
) {
    operator fun invoke() = currenciesRepository.getCurrencies()
}