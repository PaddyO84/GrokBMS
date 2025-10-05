
package com.bmsgrok.app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerId: Long,
    val title: String,
    val description: String?,
    val dateRequested: Long,
    val status: String,
    val quoteId: Long?,
    val invoiceId: Long?
)
