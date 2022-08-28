package com.currencies.feature.currency.ui.currencieslist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.currencies.feature.currency.R
import com.currencies.feature.currency.databinding.ItemCurrencyBinding
import com.currencies.feature.currency.domain.Currency

class CurrencyViewHolder(view: View, private val onCurrencyClickedListener: (Currency) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val itemCurrencyBinding: ItemCurrencyBinding = ItemCurrencyBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(currency: Currency) {
        itemCurrencyBinding.currencyName.text = currency.name
        itemCurrencyBinding.currencyRate.text = "%.2f".format(currency.rate)
        itemCurrencyBinding.root.setOnClickListener { onCurrencyClickedListener(currency) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onCurrencyClickedListener: (Currency) -> Unit
        ): CurrencyViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
            return CurrencyViewHolder(view, onCurrencyClickedListener)
        }
    }
}