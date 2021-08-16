package com.test.currencies.domain

import com.test.currencies.data.CurrenciesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class FetchCurrenciesInteractorTest {

    @Mock
    private lateinit var repository: CurrenciesRepository
    private lateinit var interactor: FetchCurrenciesInteractor

    @Before
    fun setUp() {
        interactor = FetchCurrenciesInteractor(repository, Dispatchers.Unconfined)
    }

    @Test
    fun fetchCurrencies() {
        // TODO use runBlockingTest instead
        runBlocking {
            val list = listOf(Currency("Name1", 1f), Currency("Name2", 2f))
            `when`(repository.fetchCurrencies()).thenReturn(flowOf(list))
            interactor.fetchCurrencies()
                .collect { assertTrue(it == list) }
        }
        verify(repository, times(1)).fetchCurrencies()
    }
}