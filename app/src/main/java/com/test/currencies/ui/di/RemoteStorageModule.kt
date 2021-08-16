package com.test.currencies.ui.di

import com.test.currencies.data.RemoteStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class RemoteStorageModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(): RemoteStorage {
        return RemoteStorage()
    }
}