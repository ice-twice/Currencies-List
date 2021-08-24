package com.test.currencies.ui.currencieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.currencies.domain.FetchCurrenciesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val fetchCurrenciesInteractor: FetchCurrenciesInteractor
) : ViewModel() {
    val uiState = MutableStateFlow<CurrenciesListUiState>(CurrenciesListUiState.Empty)

    init {
        uiState.value = CurrenciesListUiState.ViewModelCreated
    }

    fun onFetchCurrencies() {
        uiState.value = CurrenciesListUiState.Loading
        viewModelScope.launch {
            fetchCurrenciesInteractor.fetchCurrencies()
                .catch {
                    uiState.value = CurrenciesListUiState.Error(it.toString())
                }
                .collect {
                    Log.d(TAG, "collect")
                    uiState.value = CurrenciesListUiState.Success(it)
                }
        }
    }

    fun onErrorShowed() {
        uiState.value = CurrenciesListUiState.Empty
    }

    companion object {
        const val TAG = "CurrenciesListViewModel"
    }
}