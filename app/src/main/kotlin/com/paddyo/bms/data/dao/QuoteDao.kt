package com.paddyo.bms.data.dao
import androidx.room.*
import com.paddyo.bms.data.entities.Quote
import kotlinx.coroutines.flow.Flow
@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes WHERE jobId = :jobId")
    fun getQuotesForJob(jobId: Long): Flow<List<Quote>>
    @Insert
    suspend fun insert(quote: Quote)
    @Update
    suspend fun update(quote: Quote)
    @Delete
    suspend fun delete(quote: Quote)
}