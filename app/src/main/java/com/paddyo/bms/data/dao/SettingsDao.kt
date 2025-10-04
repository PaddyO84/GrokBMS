package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paddyo.bms.data.entities.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Insert
    suspend fun insert(settings: Settings)

    @Update
    suspend fun update(settings: Settings)

    @Query("SELECT * FROM settings LIMIT 1")
    fun getSettings(): Flow<Settings?>
}
