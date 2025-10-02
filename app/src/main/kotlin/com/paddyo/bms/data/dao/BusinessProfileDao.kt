package com.paddyo.bms.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paddyo.bms.data.entities.BusinessProfile
import kotlinx.coroutines.flow.Flow
@Dao
interface BusinessProfileDao {
    @Query("SELECT * FROM business_profile WHERE id = 1")
    fun getBusinessProfile(): Flow<BusinessProfile>
    @Insert
    suspend fun insert(profile: BusinessProfile)
    @Update
    suspend fun update(profile: BusinessProfile)
}