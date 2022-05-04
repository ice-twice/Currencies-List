package com.currencies.ui.currencieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencies.domain.FetchCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val fetchCurrenciesUseCase: FetchCurrenciesUseCase
) : ViewModel() {
    val uiState = MutableStateFlow(CurrenciesListUiState())
    private var job: Job? = null

    init {
        onFetchCurrencies()
    }

    fun onFetchCurrencies(refresh: Boolean = false) {
        job?.cancel()
        uiState.value = uiState.value.copy(isLoading = true)
        job = viewModelScope.launch {
            fetchCurrenciesUseCase.fetchCurrencies(refresh)
                .catch { error ->
                    uiState.update {
                        it.copy(error = error.toString())
                    }
                }
                .onCompletion {
                    uiState.update {
                        it.copy(isLoading = false)
                    }
                }
                .collect { currencies ->
                    uiState.update {
                        it.copy(currencies = currencies)
                    }
                }
        }
    }

    fun onErrorShowed() = uiState.update {
        it.copy(error = null)
    }
}