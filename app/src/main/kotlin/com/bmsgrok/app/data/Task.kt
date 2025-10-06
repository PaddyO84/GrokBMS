
package com.bmsgrok.app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jobId: Long,
    val description: String,
    val requiredDate: Long,
    val vendor: String?,
    val laborCost: Double?,
    val materialCost: Double?,
    val receiptImagePaths: List<String>?,
    val workImagePaths: List<String>?
)
