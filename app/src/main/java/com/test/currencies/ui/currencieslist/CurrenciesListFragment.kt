package com.test.currencies.ui.currencieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.test.currencies.databinding.CurrenciesListFragmentBinding
import com.test.currencies.ui.currencieslist.adapter.CurrenciesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
        createAndSetListAdapter()
        initializeUiState()
        initializeSwipeRefresh()
        return binding.root
    }

    private fun createAndSetListAdapter() {
        currenciesAdapter = CurrenciesAdapter()
        binding.currenciesList.adapter = currenciesAdapter
    }

    private fun initializeSwipeRefresh() {
        binding.swipeToRefresh.setOnRefreshListener { currenciesListViewModel.onFetchCurrencies() }
    }

    private fun initializeUiState() {
        lifecycleScope.launchWhenStarted {
            currenciesListViewModel.uiState.collect { state ->
                when (state) {
                    is CurrenciesListUiState.ViewModelCreated -> {
                        currenciesListViewModel.onFetchCurrencies()
                    }
                    is CurrenciesListUiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is CurrenciesListUiState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.swipeToRefresh.isRefreshing = false
                        currenciesAdapter.submitList(state.currencies)
                    }
                    is CurrenciesListUiState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.swipeToRefresh.isRefreshing = false
                        Snackbar.make(binding.root, state.errorText, Snackbar.LENGTH_SHORT).show()
                        currenciesListViewModel.onErrorShowed()
                    }
                    is CurrenciesListUiState.Empty -> {
                        // empty
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}