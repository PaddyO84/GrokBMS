package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "jobs",
    foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = ["id"],
        childColumns = ["customerId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["customerId"])]
)
data class Job(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerId: Long,
    val description: String,
    val dateRequested: String,
    val status: String,
    val labourCosts: Double,
    val materialCosts: Double
)
