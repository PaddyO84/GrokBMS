package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = Job::class,
        parentColumns = ["id"],
        childColumns = ["jobId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jobId: Int,
    val description: String,
    val dueDate: LocalDateTime
)
