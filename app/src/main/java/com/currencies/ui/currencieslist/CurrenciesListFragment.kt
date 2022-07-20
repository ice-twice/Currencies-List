package com.currencies.ui.currencieslist

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.currencies.databinding.CurrenciesListFragmentBinding
import com.currencies.domain.Currency
import com.currencies.ui.currencieslist.adapter.CurrenciesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CurrenciesListFragment : Fragment() {
    private var _binding: CurrenciesListFragmentBinding? = null
    private val binding get() = _binding!!
    private val currenciesListViewModel by viewModels<CurrenciesListViewModel>()
    private lateinit var currenciesAdapter: CurrenciesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = CurrenciesListFragmentBinding.inflate(inflater, container, false)
        setupAdapter()
        observeUiState()
        initializeSwipeRefresh()
        return binding.root
    }

    private fun setupAdapter() {
        val onCurrencyClickedListener: (Currency) -> Unit =
            { currency -> searchForCurrencyName(currency.name) }
        currenciesAdapter = CurrenciesAdapter(onCurrencyClickedListener)
        binding.currenciesList.adapter = currenciesAdapter
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.currenciesList.addItemDecoration(decoration)
    }

    private fun searchForCurrencyName(currencyName: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, currencyName)
        startActivity(intent)
    }

    private fun observeUiState() =
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val uiState = currenciesListViewModel.uiState
                launch {
                    uiState
                        .distinctUntilChangedBy { it.isLoading }
                        .collect { uiState ->
                            binding.swipeToRefresh.isRefreshing = uiState.isLoading
                        }
                }

                launch {
                    uiState
                        .distinctUntilChangedBy { it.currencies } // may be slow
                        .collect { uiState ->
                            currenciesAdapter.submitList(uiState.currencies)
                        }
                }

                launch {
                    uiState
                        .map { it.error }
                        .filterNotNull()
                        .collect { error ->
                            Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
                            currenciesListViewModel.onErrorShowed()
                        }
                }
            }
        }

    private fun initializeSwipeRefresh() =
        binding.swipeToRefresh.setOnRefreshListener { currenciesListViewModel.onRefreshCurrencies() }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}