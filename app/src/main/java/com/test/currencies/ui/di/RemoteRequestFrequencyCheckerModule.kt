package com.test.currencies.ui.di

import android.content.Context
import com.test.currencies.data.RemoteRequestFrequencyChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class RemoteRequestFrequencyCheckerModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(@ApplicationContext context: Context): RemoteRequestFrequencyChecker {
        return RemoteRequestFrequencyChecker(context)
    }
}