package com.currencies.ui.currencieslist

import com.currencies.domain.Currency

data class CurrenciesListUiState(
    val isLoading: Boolean = false,
    val currencies: List<Currency>? = null,
    val error: String? = null
)

