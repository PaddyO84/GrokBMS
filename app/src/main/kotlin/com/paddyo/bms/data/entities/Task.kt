package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = Job::class,
    parentColumns = ["id"],
    childColumns = ["jobId"],
    onDelete = ForeignKey.CASCADE
)])
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jobId: Long,
    val description: String,
    val dateRequired: Long,
    val vendor: String?,
    val labourCost: Double,
    val labourType: String,
    val labourDurationHours: Double,
    val materialCost: Double,
    val materialType: String,
    val receiptImagePath: String?,
    val workImagePath: String?
)