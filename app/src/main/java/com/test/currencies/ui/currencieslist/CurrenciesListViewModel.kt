package com.test.currencies.ui.currencieslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.currencies.domain.Currency
import com.test.currencies.domain.FetchCurrenciesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesListViewModel @Inject constructor(private val fetchCurrenciesInteractor: FetchCurrenciesInteractor) :
    ViewModel() {
    val viewModelCreation = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()
    val message = MutableLiveData<String?>()
    val currenciesList = MutableLiveData<List<Currency>>()

    companion object {
        val TAG = CurrenciesListViewModel::class.simpleName
    }

    init {
        viewModelCreation.value = true
    }

    fun viewModelCreationConsumed() {
        viewModelCreation.value = false
    }

    fun onFetchCurrencies() {
        progressBar.value = true
        viewModelScope.launch {
            fetchCurrenciesInteractor.fetchCurrencies()
                .onCompletion {
                    Log.d(TAG, "onCompletion ${it?.cause}")
                    progressBar.value = false
                }
                .catch {
                    message.value = it.toString()
                }
                .collect {
                    Log.d(TAG, "collect")
                    currenciesList.value = it
                }
        }
    }

    fun onMessageShowed() {
        message.value = null
    }
}