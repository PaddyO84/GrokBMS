package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoices")
data class Invoice(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jobId: Long,
    val customerId: Long,
    val totalAmount: Double,
    val issueDate: Long,
    val status: String
)