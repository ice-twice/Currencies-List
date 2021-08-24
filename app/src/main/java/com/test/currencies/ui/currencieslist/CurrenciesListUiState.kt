package com.test.currencies.ui.currencieslist

import com.test.currencies.domain.Currency

sealed interface CurrenciesListUiState {
    object Empty : CurrenciesListUiState
    object ViewModelCreated : CurrenciesListUiState
    object Loading : CurrenciesListUiState
    data class Success(val currencies: List<Currency>) : CurrenciesListUiState
    data class Error(val errorText: String) : CurrenciesListUiState
}