package com.currencies.ui.di

import com.currencies.data.CurrenciesRepositoryImpl
import com.currencies.domain.GetCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class GetCurrenciesUseCaseModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(currenciesRepository: CurrenciesRepositoryImpl) =
        GetCurrenciesUseCase(currenciesRepository)
}