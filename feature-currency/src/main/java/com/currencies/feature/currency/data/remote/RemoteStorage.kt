package com.currencies.feature.currency.data.remote

import com.currencies.feature.currency.domain.Currency
import com.currencies.feature.currency.domain.exception.FetchCurrenciesDataException
import com.currencies.feature.currency.domain.exception.ParseCurrenciesDataException
import com.currencies.feature.currency.domain.extensions.foldSuccess
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import javax.inject.Inject

class RemoteStorage @Inject constructor() {

    fun fetchCurrencies(): Result<List<Currency>> {
        val documentResult: Result<Document> = try {
            Result.success(Jsoup.connect(URL).get())
        } catch (e: Exception) {
            Result.failure(FetchCurrenciesDataException())
        }
        return documentResult.foldSuccess(
            onSuccess = { document ->
                val currenciesData = document.select("table[id^=crypto_currencies_]")
                map(currenciesData)
            },
            onSuccessFailed = {
                ParseCurrenciesDataException()
            })
    }

    private fun map(currenciesData: Elements) = currenciesData.map {
        val currencyData = it.select("tbody tr")[0]
        val name = currencyData.child(1).text()
        val rate = currencyData.child(3).text()
            .replace(".", "")
            .replace(",", ".")
            .toFloat()
        Currency(name, rate)
    }

    private companion object {
        private val URL = byteArrayOf(
            104,
            116,
            116,
            112,
            115,
            58,
            47,
            47,
            114,
            117,
            46,
            105,
            110,
            118,
            101,
            115,
            116,
            105,
            110,
            103,
            46,
            99,
            111,
            109,
            47,
            99,
            114,
            121,
            112,
            116,
            111,
            47,
            99,
            117,
            114,
            114,
            101,
            110,
            99,
            121,
            45,
            112,
            97,
            105,
            114,
            115,
            63,
            101,
            120,
            99,
            104,
            97,
            110,
            103,
            101,
            61,
            49,
            48,
            50,
            55,
            38,
            99,
            50,
            61,
            49,
            50
        ).decodeToString()
    }
}