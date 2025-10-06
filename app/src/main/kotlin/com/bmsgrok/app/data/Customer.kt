
package com.bmsgrok.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val companyName: String?,
    val email: String?,
    val phoneNumbers: List<String>,
    val roleTitle: String?
)
