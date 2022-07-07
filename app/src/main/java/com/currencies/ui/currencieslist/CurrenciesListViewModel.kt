package com.currencies.ui.currencieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencies.domain.GetCurrenciesUseCase
import com.currencies.domain.UpdateCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val updateCurrenciesUseCase: UpdateCurrenciesUseCase
) : ViewModel() {
    val uiState = MutableStateFlow(CurrenciesListUiState())
    private val updateCurrenciesActionFlow = MutableSharedFlow<Unit>()

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
        updateCurrenciesActionFlow
            .onStart { emit(Unit) }
            .onEach {
                uiState.value = uiState.value.copy(isLoading = true)
            }
            .onEach {
                try {
                    updateCurrenciesUseCase()
                } catch (e: Exception) {
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

    fun onUpdateCurrencies() {
        viewModelScope.launch {
            updateCurrenciesActionFlow.emit(Unit)
        }
    }

    fun onErrorShowed() = uiState.update {
        it.copy(error = null)
    }
}