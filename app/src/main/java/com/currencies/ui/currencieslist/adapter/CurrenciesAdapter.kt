package com.currencies.ui.currencieslist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.currencies.domain.Currency

class CurrenciesAdapter(private val onCurrencyClickedListener: (Currency) -> Unit) :
    ListAdapter<Currency, CurrencyViewHolder>(CurrenciesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder.create(parent, onCurrencyClickedListener)

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) =
        holder.bind(getItem(position))
}
