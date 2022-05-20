package com.currencies.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.currencies.data.RemoteStorage
import com.currencies.domain.Currency
import kotlinx.coroutines.flow.Flow

class CurrenciesPagingRepository(private val remoteStorage: RemoteStorage) {

    fun createCurrenciesPagingStream(): Flow<PagingData<Currency>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            maxSize = MAX_SIZE
        ),
        pagingSourceFactory = { CurrenciesPagingSource(remoteStorage) } // TODO better to create and use CurrenciesPagingSourceFactory here
    )
        .flow

    private companion object {
        private const val PAGE_SIZE = 40
        private const val MAX_SIZE = 150
    }
}