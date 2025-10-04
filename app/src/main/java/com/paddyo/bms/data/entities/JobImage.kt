package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "job_images",
    foreignKeys = [ForeignKey(
        entity = Job::class,
        parentColumns = ["id"],
        childColumns = ["jobId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class JobImage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jobId: Int,
    val imageUri: String
)
