package com.paddyo.bms.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val backupLocation: String,
    val darkMode: Boolean,
    val reminderDaysBefore: Int
)
