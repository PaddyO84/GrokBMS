package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Delete
    suspend fun update(invoice: Invoice)

    @Delete
    suspend fun delete(invoice: Invoice)

    @Query("SELECT * FROM invoices WHERE issueDate BETWEEN :start AND :end")
    suspend fun getInvoicesForYear(start: Long, end: Long): List<Invoice>
}