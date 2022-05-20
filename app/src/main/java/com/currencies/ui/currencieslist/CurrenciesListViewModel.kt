package com.currencies.ui.currencieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencies.domain.FetchCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val fetchCurrenciesUseCase: FetchCurrenciesUseCase
) : ViewModel() {
    val uiState = MutableStateFlow(CurrenciesListUiState())
    private val fetchCurrenciesActionFlow = MutableSharedFlow<Boolean>()

    init {
        fetchCurrenciesActionFlow
            .onStart {
                emit(false)
            }
            .onEach {
                uiState.value = uiState.value.copy(isLoading = true)
            }
            .flatMapLatest { refresh ->
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
            }
            .onEach { currencies ->
                uiState.update {
                    it.copy(currencies = currencies)
                }
            }
            .shareIn(viewModelScope, SharingStarted.Eagerly, 0)
    }

    fun onFetchCurrencies(refresh: Boolean = false) {
        viewModelScope.launch {
            fetchCurrenciesActionFlow.emit(refresh)
        }
    }

    fun onErrorShowed() = uiState.update {
        it.copy(error = null)
    }
}