package com.currencies.feature.currency.ui.currencydetails

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.currencies.feature.currency.databinding.CurrencyDetailsFragmentBinding

// TODO after opening this fragment, content on CurrenciesLisFragment and here is drawing under ActionBar and StatusBar.
class CurrencyDetailsFragment : Fragment() {
    private var _binding: CurrencyDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val arguments: CurrencyDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = CurrencyDetailsFragmentBinding.inflate(inflater, container, false)
        initializeViews()
        return binding.root
    }

    private fun initializeViews() {
        binding.currencyName.text = arguments.currencyName
        binding.currencyRate.text = arguments.currencyRate.toString()
        binding.searchCurrencyButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, arguments.currencyName)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}