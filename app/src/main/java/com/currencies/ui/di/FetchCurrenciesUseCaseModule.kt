package com.currencies.ui.di

import com.currencies.data.CurrenciesRepositoryImpl
import com.currencies.domain.FetchCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ActivityRetainedComponent::class)
class FetchCurrenciesUseCaseModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(currenciesRepository: CurrenciesRepositoryImpl) =
        FetchCurrenciesUseCase(currenciesRepository, Dispatchers.IO)
}