package com.test.currencies.ui.currencieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.test.currencies.databinding.CurrenciesListFragmentBinding
import com.test.currencies.ui.currencieslist.adapter.CurrenciesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesListFragment : Fragment() {
    private var _binding: CurrenciesListFragmentBinding? = null
    private val binding get() = _binding!!

    private val currenciesListViewModel by viewModels<CurrenciesListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = CurrenciesListFragmentBinding.inflate(inflater, container, false)
        initializeViewModelCreation()
        initializeProgressView()
        initializeMessage()
        initializeList()
        initializeSwipeRefresh()
        return binding.root
    }

    private fun initializeViewModelCreation() {
        currenciesListViewModel.viewModelCreation.observe(viewLifecycleOwner) {
            if (it) {
                currenciesListViewModel.onFetchCurrencies()
                currenciesListViewModel.viewModelCreationConsumed()
            }
        }
    }

    private fun initializeProgressView() {
        currenciesListViewModel.progressBar.observe(viewLifecycleOwner, {
            it?.let {
                binding.progressBar.isVisible = it
                binding.swipeToRefresh.isRefreshing = false
            }
        })
    }

    private fun initializeMessage() {
        currenciesListViewModel.message.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                currenciesListViewModel.onMessageShowed()
            }
        }
    }

    private fun initializeList() {
        val adapter = CurrenciesAdapter()
        binding.currenciesList.adapter = adapter
        currenciesListViewModel.currenciesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun initializeSwipeRefresh() {
        binding.swipeToRefresh.setOnRefreshListener { currenciesListViewModel.onFetchCurrencies() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}