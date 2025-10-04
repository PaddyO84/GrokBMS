package com.paddyo.bms.data.dao

import androidx.room.Dao
    suspend fun insert(quote: Quote)

    @Update
    suspend fun update(quote: Quote)

    @Delete
    suspend fun delete(quote: Quote)
}