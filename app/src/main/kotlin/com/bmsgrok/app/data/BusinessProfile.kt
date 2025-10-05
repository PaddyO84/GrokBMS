
package com.bmsgrok.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_profile")
data class BusinessProfile(
    @PrimaryKey val id: Long = 1,
    val companyName: String,
    val address: String,
    val phoneNumber: String,
    val email: String,
    val vatNumber: String?,
    val logoPath: String?
)
