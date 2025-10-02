package com.paddyo.bms.data.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "job_images")
data class JobImage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jobId: Long,
    val imagePath: String,
    val date: Long
)