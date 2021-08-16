package com.test.currencies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM CurrencyEntity")
    fun getAll(): List<CurrencyEntity>

    @Insert(onConflict = REPLACE)
    fun insertAll(currencies: List<CurrencyEntity>)
}