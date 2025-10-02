package com.paddyo.bms.data.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "business_profile")
data class BusinessProfile(
    @PrimaryKey val id: Long = 1,
    val companyName: String = "",
    val companyAddress: String = "",
    val companyPhone: String = "",
    val companyEmail: String = "",
    val vatNumber: String? = null,
    val logoPath: String? = null
)