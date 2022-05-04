package com.currencies.ui.currencieslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.currencies.R
import com.currencies.domain.Currency

class CurrenciesAdapter(private val onCurrencyClickedListener: (Currency) -> Unit) :
    ListAdapter<Currency, CurrencyViewHolder>(CurrenciesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view, onCurrencyClickedListener)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) =
        holder.bind(getItem(position))
}
