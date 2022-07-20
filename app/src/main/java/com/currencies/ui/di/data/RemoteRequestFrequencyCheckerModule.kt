package com.currencies.ui.di.data

import android.content.Context
import com.currencies.data.remote.RemoteRequestFrequencyChecker
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