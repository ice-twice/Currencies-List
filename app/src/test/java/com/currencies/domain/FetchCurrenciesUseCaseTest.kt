package com.currencies.domain

import com.currencies.data.CurrenciesRepositoryImpl
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
internal class FetchCurrenciesUseCaseTest {

    @Mock
    private lateinit var repository: CurrenciesRepositoryImpl
    private lateinit var useCase: FetchCurrenciesUseCase

    @Before
    fun setUp() {
        useCase =
            FetchCurrenciesUseCase(repository, Dispatchers.Unconfined)
    }

    @Test
    fun fetchCurrencies() {
        // TODO use runBlockingTest instead
        runBlocking {
            val list = listOf(Currency("Name1", 1f), Currency("Name2", 2f))
            `when`(repository.fetchCurrencies()).thenReturn(flowOf(list))
            useCase.fetchCurrencies()
                .collect { assertTrue(it == list) }
        }
        verify(repository, times(1)).fetchCurrencies()
    }
}