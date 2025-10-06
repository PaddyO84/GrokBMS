package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paddyo.bms.data.entities.Invoice
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Insert
    suspend fun insert(invoice: Invoice)

    @Query("SELECT * FROM invoices WHERE jobId = :jobId")
    fun getInvoicesByJob(jobId: Int): Flow<List<Invoice>>
}
