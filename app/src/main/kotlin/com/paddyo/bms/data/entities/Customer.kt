package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val companyName: String?,
    val email: String,
    val phoneNumbers: List<String>,
    val roleTitle: String?
)