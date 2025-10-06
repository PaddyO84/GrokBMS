package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paddyo.bms.data.entities.Receipt
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {
    @Insert
    suspend fun insert(receipt: Receipt)

    @Query("SELECT * FROM receipts WHERE jobId = :jobId")
    fun getReceiptsByJob(jobId: Int): Flow<List<Receipt>>
}
