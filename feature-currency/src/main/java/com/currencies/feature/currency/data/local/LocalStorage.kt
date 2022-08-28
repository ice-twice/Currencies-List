package com.currencies.feature.currency.data.local

import com.currencies.feature.currency.domain.Currency
import kotlinx.coroutines.flow.map

class LocalStorage(
    private val currencyDao: CurrencyDao,
    private val currencyEntityMapper: CurrencyEntityMapper
) {
    fun getCurrencies() = currencyDao.getAll().map { currencyEntityMapper.mapFromEntity(it) }

    fun replaceCurrencies(currencies: List<Currency>) = currencyEntityMapper.mapToEntity(currencies)
        .also {
            currencyDao.insertAll(it)
        }
}