package com.currencies.ui.di

import android.content.Context
import androidx.room.Room
import com.currencies.data.local.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class CurrencyDatabaseModule {

    @ActivityRetainedScoped
    @Provides
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        CurrencyDatabase::class.java,
        "currency_database"
    ).build()
}