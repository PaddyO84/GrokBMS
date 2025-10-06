package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paddyo.bms.data.entities.BusinessProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessProfileDao {
    @Insert
    suspend fun insert(businessProfile: BusinessProfile)

    @Update
    suspend fun update(businessProfile: BusinessProfile)

    @Query("SELECT * FROM business_profile LIMIT 1")
    fun getBusinessProfile(): Flow<BusinessProfile?>
}
