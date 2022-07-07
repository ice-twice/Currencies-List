package com.currencies.ui.di

import com.currencies.data.local.CurrencyEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class CurrencyEntityMapperModule {

    @ActivityRetainedScoped
    @Provides
    fun provide() = CurrencyEntityMapper()
}