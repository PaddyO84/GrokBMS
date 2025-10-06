package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "jobs",
    foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = ["id"],
        childColumns = ["customerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Job(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val customerId: Int,
    val description: String,
    val dateRequested: LocalDateTime,
    val vendors: String // Comma-separated list
)
