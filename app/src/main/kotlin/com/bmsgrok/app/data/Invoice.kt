
package com.bmsgrok.app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "invoices",
    foreignKeys = [ForeignKey(
        entity = Job::class,
        parentColumns = ["id"],
        childColumns = ["jobId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Invoice(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jobId: Long,
    val totalAmount: Double,
    val details: String,
    val issuedDate: Long,
    val paid: Boolean
)
