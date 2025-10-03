package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipts")
data class Receipt(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jobId: Long,
    val imagePath: String,
    val amount: Double,
    val date: Long
)