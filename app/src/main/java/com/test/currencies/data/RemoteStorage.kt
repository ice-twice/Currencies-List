package com.test.currencies.data

import com.test.currencies.domain.Currency
import com.test.currencies.domain.exception.FetchCurrenciesDataException
import com.test.currencies.domain.exception.ParseCurrenciesDataException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class RemoteStorage {

    fun fetchCurrencies(): List<Currency> {
        val document: Document
        try {
            @Suppress("BlockingMethodInNonBlockingContext")
            document = Jsoup.connect(URL).get()
        } catch (e: Exception) {
            throw FetchCurrenciesDataException()
        }

        try {
            val currenciesData = document.select("table[id^=crypto_currencies_]")
            return (map(currenciesData))
        } catch (e: Exception) {
            throw ParseCurrenciesDataException()
        }
    }

    private fun map(currenciesData: Elements): List<Currency> {
        return currenciesData.map {
            val currencyData = it.select("tbody tr")[0]
            val name = currencyData.child(1).text()
            val rate = currencyData.child(3).text()
                .replace(".", "")
                .replace(",", ".")
                .toFloat()
            Currency(name, rate)
        }
    }

    companion object {
        val URL = byteArrayOf(
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