package com.test.currencies.ui.currencieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.test.currencies.domain.Currency
import com.test.currencies.domain.FetchCurrenciesInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
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

    @Mock
    private lateinit var progressObserver: Observer<Boolean>

    @Mock
    private lateinit var messageObserver: Observer<String?>

    @Mock
    private lateinit var listObserver: Observer<List<Currency>>

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = CurrenciesListViewModel(interactor)
        viewModel.progressBar.observeForever(progressObserver)
        viewModel.message.observeForever(messageObserver)
        viewModel.currenciesList.observeForever(listObserver)
    }

    //TODO pause dispatcher
    @Test
    fun onFetchCurrencies_Success() {
        val currencies = listOf(Currency("Name1", 1f), Currency("Name2", 2f))

        `when`(interactor.fetchCurrencies()).thenReturn(flowOf(currencies))

        viewModel.onFetchCurrencies()

        verify(interactor, times(1)).fetchCurrencies()
        verify(progressObserver, times(1)).onChanged(true)
        verify(progressObserver, times(1)).onChanged(false)
        verify(listObserver, times(1)).onChanged(currencies)
        verify(messageObserver, times(0)).onChanged(any())
    }

    @Test
    fun onFetchCurrencies_Error() {
        `when`(interactor.fetchCurrencies()).thenReturn(flow { throw NullPointerException() })

        viewModel.onFetchCurrencies()

        verify(interactor, times(1)).fetchCurrencies()
        verify(progressObserver, times(1)).onChanged(true)
        verify(progressObserver, times(1)).onChanged(false)
        verify(listObserver, times(0)).onChanged(any())
        verify(messageObserver, times(1)).onChanged("java.lang.NullPointerException")
    }
}