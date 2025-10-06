package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paddyo.bms.data.entities.BusinessProfile

@Dao
interface BusinessProfileDao {
    @Insert
    suspend fun insert(profile: BusinessProfile)

    @Query("SELECT * FROM business_profile LIMIT 1")
    suspend fun getBusinessProfile(): BusinessProfile?
}
