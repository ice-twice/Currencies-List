package com.currencies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.currencies.data.RemoteStorage
import com.currencies.domain.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min

class CurrenciesPagingSource(private val remoteStorage: RemoteStorage) :
    PagingSource<Int, Currency>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Currency> {
        return withContext(Dispatchers.IO) { // TODO I suppose it works asynchronous automatically but it is not, inject dispatcher
            val currenciesResult = kotlin.runCatching { remoteStorage.fetchCurrencies() }
            currenciesResult.fold({ allCurrencies ->
                val startKey = params.key ?: STARTING_KEY
                val endKey = min(startKey + params.loadSize, allCurrencies.size) - 1
                val currencies = allCurrencies.subList(startKey, endKey + 1)
                val prevKey = when (startKey) {
                    STARTING_KEY -> null
                    else -> max(STARTING_KEY, startKey - params.loadSize)
                }
                val nextKey = when (endKey) {
                    allCurrencies.size - 1 -> null
                    else -> endKey + 1
                }
                LoadResult.Page(currencies, prevKey, nextKey)
            }, {
                LoadResult.Error(it)
            })
//        LoadResult.Invalid TODO
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Currency>): Int? =
        state.anchorPosition // TODO check

    private companion object {
        private const val STARTING_KEY = 0
    }
}