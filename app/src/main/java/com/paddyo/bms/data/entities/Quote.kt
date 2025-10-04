package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "quotes",
    foreignKeys = [ForeignKey(
        entity = Job::class,
        parentColumns = ["id"],
        childColumns = ["jobId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Quote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jobId: Int,
    val amount: Double,
    val createdAt: LocalDateTime
)
