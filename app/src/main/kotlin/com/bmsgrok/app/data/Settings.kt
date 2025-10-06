
package com.bmsgrok.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Long = 1,
    val backupLocation: String?,
    val theme: String,
    val reminderDaysBefore: Int
)
