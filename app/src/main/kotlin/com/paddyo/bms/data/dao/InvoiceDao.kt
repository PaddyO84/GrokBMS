package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paddyo.bms.data.entities.Invoice

@Dao
interface InvoiceDao {
    @Insert
    suspend fun insert(invoice: Invoice)

    @Query("SELECT * FROM invoices WHERE jobId = :jobId")
    suspend fun getInvoicesForJob(jobId: Long): List<Invoice>
}
