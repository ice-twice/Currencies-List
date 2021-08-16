package com.test.currencies.ui.di

import com.test.currencies.data.CurrenciesRepository
import com.test.currencies.domain.FetchCurrenciesInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ActivityRetainedComponent::class)
class FetchCurrenciesInteractorModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(currenciesRepository: CurrenciesRepository): FetchCurrenciesInteractor {
        return FetchCurrenciesInteractor(currenciesRepository, Dispatchers.Default)
    }
}