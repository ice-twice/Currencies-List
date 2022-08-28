package com.currencies.feature.currency.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM CurrencyEntity")
    fun getAll(): Flow<List<CurrencyEntity>>

    @Insert(onConflict = REPLACE)
    fun insertAll(currencies: List<CurrencyEntity>)
}