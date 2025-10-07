package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BusinessProfile(
    @PrimaryKey val id: Long = 1,
    val companyName: String,
    val address: String,
    val phone: String,
    val email: String,
    val vatNumber: String?,
    val logoPath: String?
)