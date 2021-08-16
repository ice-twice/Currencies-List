package com.test.currencies.data

import com.test.currencies.data.local.LocalStorage
import com.test.currencies.domain.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrenciesRepository(
    private val remoteStorage: RemoteStorage,
    private val localStorage: LocalStorage,
    private val requestFrequencyChecker: RemoteRequestFrequencyChecker,
) {
    fun fetchCurrencies(): Flow<List<Currency>> {
        return flow {
            val currencies: List<Currency>
            if (requestFrequencyChecker.isRequestForbidden()) {
                currencies = localStorage.fetchCurrencies()
            } else {
                currencies = remoteStorage.fetchCurrencies()
                requestFrequencyChecker.requestDone()
                localStorage.replaceCurrencies(currencies)
            }
            emit(currencies)
        }
    }
}