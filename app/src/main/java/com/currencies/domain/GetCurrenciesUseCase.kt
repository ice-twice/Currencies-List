package com.currencies.domain

class GetCurrenciesUseCase(
    private val currenciesRepository: CurrenciesRepository
) {
    operator fun invoke() = currenciesRepository.getCurrencies()
}