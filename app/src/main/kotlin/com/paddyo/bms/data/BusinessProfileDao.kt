package com.paddyo.bms.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paddyo.bms.data.entities.BusinessProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessProfileDao {
    @Query("SELECT * FROM BusinessProfile WHERE id = 1")
    fun getBusinessProfile(): Flow<BusinessProfile?>

    @Insert
    suspend fun insertBusinessProfile(profile: BusinessProfile)

    @Update
    suspend fun updateBusinessProfile(profile: BusinessProfile)
}