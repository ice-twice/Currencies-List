package com.currencies.data.local

import com.currencies.domain.Currency

class LocalStorage(private val currencyDao: CurrencyDao) {

    fun fetchCurrencies() = currencyDao.getAll().map { Currency(it.name, it.rate) }

    fun replaceCurrencies(currencies: List<Currency>) =
        currencies.map { CurrencyEntity(it.name, it.rate) }
            .also {
                currencyDao.insertAll(it)
            }
}