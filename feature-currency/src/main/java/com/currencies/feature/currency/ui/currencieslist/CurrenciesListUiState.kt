package com.currencies.feature.currency.ui.currencieslist

import com.currencies.feature.currency.domain.Currency

data class CurrenciesListUiState(
    val isLoading: Boolean = false,
    val currencies: List<Currency>? = null,
    val error: String? = null
)

