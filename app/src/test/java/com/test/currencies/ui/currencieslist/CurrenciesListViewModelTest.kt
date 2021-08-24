package com.test.currencies.ui.currencieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.currencies.domain.Currency
import com.test.currencies.domain.FetchCurrenciesInteractor
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

// TODO fail because of Log.d calls
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrenciesListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var interactor: FetchCurrenciesInteractor
    private lateinit var viewModel: CurrenciesListViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = CurrenciesListViewModel(interactor)
    }

    @Test
    fun onFetchCurrencies_Success() = runBlockingTest {
        val currencies = listOf(Currency("Name1", 1f), Currency("Name2", 2f))
        `when`(interactor.fetchCurrencies()).thenReturn(flowOf(currencies))

        val results = mutableListOf<CurrenciesListUiState>()
        val job = launch {
            viewModel.uiState.toList(results)
        }

        viewModel.onFetchCurrencies()

        job.cancel()

        verify(interactor, times(1)).fetchCurrencies()
        assertTrue(results.size == 3)
        assertEquals(results[0], CurrenciesListUiState.ViewModelCreated)
        assertEquals(results[1], CurrenciesListUiState.Loading)
        assertEquals((results[2] as CurrenciesListUiState.Success).currencies, currencies)
    }

    @Test
    fun onFetchCurrencies_Error() = runBlockingTest {
        `when`(interactor.fetchCurrencies()).thenReturn(flow { throw NullPointerException() })

        val results = mutableListOf<CurrenciesListUiState>()
        val job = launch {
            viewModel.uiState.toList(results)
        }

        viewModel.onFetchCurrencies()

        job.cancel()

        verify(interactor, times(1)).fetchCurrencies()
        assertTrue(results.size == 3)
        assertEquals(results[0], CurrenciesListUiState.ViewModelCreated)
        assertEquals(results[1], CurrenciesListUiState.Loading)
        assertEquals(
            (results[2] as CurrenciesListUiState.Error).errorText,
            "java.lang.NullPointerException"
        )
    }
}