package com.currencies.ui.currencieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencies.domain.GetCurrenciesUseCase
import com.currencies.domain.RefreshCurrenciesUseCase
import com.currencies.domain.tryCancellable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase
) : ViewModel() {
    val uiState = MutableStateFlow(CurrenciesListUiState())
    private val refreshCurrenciesActionFlow = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch {
            getCurrenciesUseCase().collect { currencies ->
                uiState.update {
                    it.copy(currencies = currencies)
                }
            }
        }

        // this code can be written in imperative style. this is used only for example and can be
        // useful for some complex cases.
        refreshCurrenciesActionFlow
            .onStart { emit(Unit) }
            .onEach {
                uiState.value = uiState.value.copy(isLoading = true)
            }
            .onEach {
                tryCancellable {
                    refreshCurrenciesUseCase()
                }.onFailure { e ->
                    uiState.update {
                        it.copy(error = e.toString())
                    }
                }
            }
            .onEach {
                uiState.value = uiState.value.copy(isLoading = false)
            }
            .shareIn(viewModelScope, SharingStarted.Eagerly, 0)
    }

    fun onRefreshCurrencies() {
        viewModelScope.launch {
            refreshCurrenciesActionFlow.emit(Unit)
        }
    }

    fun onErrorShowed() = uiState.update {
        it.copy(error = null)
    }
}