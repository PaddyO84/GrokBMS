package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Long = 1,
    val backupLocation: String = "",
    val theme: String = "Light",
    val reminderFrequency: String = "Daily"
)
