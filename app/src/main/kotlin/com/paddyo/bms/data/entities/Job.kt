package com.paddyo.bms.data.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerId: Long,
    val title: String,
    val description: String,
    val dateRequested: Long,
    val status: String
)