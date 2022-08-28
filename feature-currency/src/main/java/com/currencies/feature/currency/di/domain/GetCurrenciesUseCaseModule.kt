package com.currencies.feature.currency.di.domain

import com.currencies.feature.currency.domain.CurrenciesRepository
import com.currencies.feature.currency.domain.GetCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class GetCurrenciesUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provide(currenciesRepository: CurrenciesRepository) =
        GetCurrenciesUseCase(currenciesRepository)
}