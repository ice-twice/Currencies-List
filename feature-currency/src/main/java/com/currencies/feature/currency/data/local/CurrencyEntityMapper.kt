package com.currencies.feature.currency.data.local

import com.currencies.feature.currency.domain.Currency
import javax.inject.Inject

class CurrencyEntityMapper @Inject constructor() {
    fun mapFromEntity(list: List<CurrencyEntity>): List<Currency> =
        list.map { Currency(it.name, it.rate) }

    fun mapToEntity(list: List<Currency>): List<CurrencyEntity> =
        list.map { CurrencyEntity(it.name, it.rate) }
}