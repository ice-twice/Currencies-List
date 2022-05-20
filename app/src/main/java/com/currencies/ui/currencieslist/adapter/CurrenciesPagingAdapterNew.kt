package com.currencies.ui.currencieslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.currencies.R
import com.currencies.domain.Currency

class CurrenciesPagingAdapterNew(private val onCurrencyClickedListener: (Currency) -> Unit) :
    PagingDataAdapter<Currency, CurrencyViewHolder>(CurrenciesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view, onCurrencyClickedListener)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        // TODO what to do with null from getItem()?
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}
