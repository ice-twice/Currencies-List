package com.currencies.feature.currency.ui.currencieslist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.currencies.feature.currency.domain.Currency

class CurrenciesDiffUtil : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem == newItem
}