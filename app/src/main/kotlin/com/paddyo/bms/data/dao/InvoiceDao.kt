package com.paddyo.bms.data.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paddyo.bms.data.entities.Invoice
import kotlinx.coroutines.flow.Flow
@Dao
interface InvoiceDao {
    @Query("SELECT * FROM invoices WHERE jobId = :jobId")
    fun getInvoicesForJob(jobId: Long): Flow<List<Invoice>>
    @Insert
    suspend fun insert(invoice: Invoice)
    @Update
    suspend fun update(invoice: Invoice)
    @Delete
    suspend fun delete(invoice: Invoice)
    @Query("SELECT * FROM invoices WHERE issueDate BETWEEN :start AND :end")
    suspend fun getInvoicesForYear(start: Long, end: Long): List<Invoice>
}