package com.currencies.ui.currencieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.currencies.databinding.CurrenciesListFragmentBinding
import com.currencies.domain.Currency
import com.currencies.ui.currencieslist.adapter.CurrenciesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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
            { currency -> showCurrencyDetailsScreen(currency) }
        currenciesAdapter = CurrenciesAdapter(onCurrencyClickedListener)
        binding.currenciesList.adapter = currenciesAdapter
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.currenciesList.addItemDecoration(decoration)
    }

    private fun showCurrencyDetailsScreen(currency: Currency) {
        val direction =
            CurrenciesListFragmentDirections.actionWelcomeFragmentToCurrencyDetailsFragment(
                currency.name,
                currency.rate
            )
        findNavController().navigate(direction)
    }

    private fun observeUiState() =
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val uiState = currenciesListViewModel.uiState
                uiState
                    .distinctUntilChangedBy { it.isLoading }
                    .onEach {
                        binding.swipeToRefresh.isRefreshing = it.isLoading
                    }
                    .launchIn(this)

                uiState
                    .distinctUntilChangedBy { it.currencies } // may be slow
                    .onEach {
                        currenciesAdapter.submitList(it.currencies)
                    }
                    .launchIn(this)

                uiState
                    .map { it.error }
                    .filterNotNull()
                    .onEach {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                        currenciesListViewModel.onErrorShowed()
                    }
                    .launchIn(this)
            }
        }

    private fun initializeSwipeRefresh() =
        binding.swipeToRefresh.setOnRefreshListener { currenciesListViewModel.onRefreshCurrencies() }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}