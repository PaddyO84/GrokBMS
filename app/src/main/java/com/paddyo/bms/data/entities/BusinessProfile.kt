package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_profile")
data class BusinessProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val companyName: String,
    val companyAddress: String,
    val companyPhone: String,
    val companyEmail: String,
    val vatNumber: String?,
    val logoUri: String?
)
