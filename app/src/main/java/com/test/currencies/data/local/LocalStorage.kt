package com.test.currencies.data.local

import com.test.currencies.domain.Currency

class LocalStorage(private val db: CurrencyDatabase) {

    fun fetchCurrencies(): List<Currency> {
        return db.currencyDao().getAll().map {
            Currency(it.name, it.rate)
        }
    }

    fun replaceCurrencies(currencies: List<Currency>) {
        currencies.map { CurrencyEntity(it.name, it.rate) }
            .also {
                db.currencyDao().insertAll(it)
            }
    }
}