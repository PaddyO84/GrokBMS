package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jobId: Long,
    val description: String,
    val requiredDate: Long,
    val vendor: String?,
    val laborCost: Double,
    val laborType: String,
    val materialCost: Double,
    val materialType: String
)