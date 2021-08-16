package com.test.currencies.ui.currencieslist.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.test.currencies.databinding.ItemCurrencyBinding
import com.test.currencies.domain.Currency

class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val itemCurrencyBinding: ItemCurrencyBinding = ItemCurrencyBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(currency: Currency) {
        itemCurrencyBinding.currencyName.text = currency.name
        itemCurrencyBinding.currencyRate.text = "%.2f".format(currency.rate)
    }
}