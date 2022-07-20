package com.currencies.ui.di.domain

import com.currencies.domain.CurrenciesRepository
import com.currencies.domain.RefreshCurrenciesPeriodicallyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RefreshCurrenciesPeriodicallyUseCaseModule {

    @Singleton
    @Provides
    fun provide(currenciesRepository: CurrenciesRepository) =
        RefreshCurrenciesPeriodicallyUseCase(currenciesRepository)
}