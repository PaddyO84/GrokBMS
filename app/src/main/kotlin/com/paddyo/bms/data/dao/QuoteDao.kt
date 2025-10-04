package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paddyo.bms.data.entities.Quote

@Dao
interface QuoteDao {
    @Insert
    suspend fun insert(quote: Quote)

    @Query("SELECT * FROM quotes WHERE jobId = :jobId")
    suspend fun getQuotesForJob(jobId: Long): List<Quote>
}
