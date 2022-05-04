package com.currencies.ui.currencieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.currencies.domain.Currency
import com.currencies.domain.FetchCurrenciesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrenciesListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: FetchCurrenciesUseCase
    private lateinit var viewModel: CurrenciesListViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = CurrenciesListViewModel(useCase)
    }

    @Test
    fun onFetchCurrencies_Success() = runBlockingTest {
        val currencies = listOf(Currency("Name1", 1f), Currency("Name2", 2f))
        `when`(useCase.fetchCurrencies()).thenReturn(flowOf(currencies))

        val results = mutableListOf<CurrenciesListUiState>()
        val job = launch {
            viewModel.uiState.toList(results)
        }

        viewModel.onFetchCurrencies()

        job.cancel()

        verify(useCase, times(1)).fetchCurrencies()
        assertTrue(results.size == 3)
        assertEquals(results[0], CurrenciesListUiState.ViewModelCreated)
        assertEquals(results[1], CurrenciesListUiState.Loading)
        assertEquals((results[2] as CurrenciesListUiState.Success).currencies, currencies)
    }

    @Test
    fun onFetchCurrencies_Error() = runBlockingTest {
        `when`(useCase.fetchCurrencies()).thenReturn(flow { throw NullPointerException() })

        val results = mutableListOf<CurrenciesListUiState>()
        val job = launch {
            viewModel.uiState.toList(results)
        }

        viewModel.onFetchCurrencies()

        job.cancel()

        verify(useCase, times(1)).fetchCurrencies()
        assertTrue(results.size == 3)
        assertEquals(results[0], CurrenciesListUiState.ViewModelCreated)
        assertEquals(results[1], CurrenciesListUiState.Loading)
        assertEquals(
            (results[2] as CurrenciesListUiState.Error).errorText,
            "java.lang.NullPointerException"
        )
    }
}