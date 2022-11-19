package com.currencies.feature.currency.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        private const val DB_NAME = "currency_database"

        fun getInstance(context: Context) = Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}