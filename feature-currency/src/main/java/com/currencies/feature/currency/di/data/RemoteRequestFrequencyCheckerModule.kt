package com.currencies.feature.currency.di.data

import android.content.Context
import com.currencies.feature.currency.data.remote.RemoteRequestFrequencyChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteRequestFrequencyCheckerModule {

    @Singleton
    @Provides
    fun provide(@ApplicationContext context: Context) = RemoteRequestFrequencyChecker(context)
}