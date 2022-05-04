package com.currencies.ui.currencieslist.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.currencies.databinding.ItemCurrencyBinding
import com.currencies.domain.Currency

class CurrencyViewHolder(view: View, private val onCurrencyClickedListener: (Currency) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val itemCurrencyBinding: ItemCurrencyBinding = ItemCurrencyBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(currency: Currency) {
        itemCurrencyBinding.currencyName.text = currency.name
        itemCurrencyBinding.currencyRate.text = "%.2f".format(currency.rate)
        itemCurrencyBinding.root.setOnClickListener { onCurrencyClickedListener(currency) }
    }
}