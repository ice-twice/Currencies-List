package com.currencies.feature.currency.di.domain

import com.currencies.feature.currency.domain.CurrenciesRepository
import com.currencies.feature.currency.domain.RefreshCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class RefreshCurrenciesUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provide(currenciesRepository: CurrenciesRepository) =
        RefreshCurrenciesUseCase(currenciesRepository, Dispatchers.IO)
}