package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paddyo.bms.data.entities.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert
    suspend fun insert(quote: Quote)

    @Query("SELECT * FROM quotes WHERE jobId = :jobId")
    fun getQuotesByJob(jobId: Int): Flow<List<Quote>>
}
