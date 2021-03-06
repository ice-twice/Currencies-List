package com.currencies.data.local

import com.currencies.domain.Currency
import javax.inject.Inject

class CurrencyEntityMapper @Inject constructor() {
    fun mapFromEntity(list: List<CurrencyEntity>): List<Currency> =
        list.map { Currency(it.name, it.rate) }

    fun mapToEntity(list: List<Currency>): List<CurrencyEntity> =
        list.map { CurrencyEntity(it.name, it.rate) }
}