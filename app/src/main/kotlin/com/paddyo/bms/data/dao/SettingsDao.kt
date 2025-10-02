package com.paddyo.bms.data.dao
import androidx.room.*
import com.paddyo.bms.data.entities.Settings
import kotlinx.coroutines.flow.Flow
@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<Settings>
    @Insert
    suspend fun insert(settings: Settings)
    @Update
    suspend fun update(settings: Settings)
}