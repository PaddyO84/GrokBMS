package com.paddyo.bms.data.dao
import androidx.room.*
import com.paddyo.bms.data.entities.Receipt
import kotlinx.coroutines.flow.Flow
@Dao
interface ReceiptDao {
    @Query("SELECT * FROM receipts WHERE jobId = :jobId")
    fun getReceiptsForJob(jobId: Long): Flow<List<Receipt>>
    @Insert
    suspend fun insert(receipt: Receipt)
    @Update
    suspend fun update(receipt: Receipt)
    @Delete
    suspend fun delete(receipt: Receipt)
    @Query("SELECT * FROM receipts WHERE date BETWEEN :start AND :end")
    suspend fun getReceiptsForYear(start: Long, end: Long): List<Receipt>
}