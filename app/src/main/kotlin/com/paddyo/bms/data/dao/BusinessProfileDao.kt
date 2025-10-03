package com.paddyo.bms.data.dao

import androidx.room.Dao
    suspend fun insert(profile: BusinessProfile)

    @Update
    suspend fun update(profile: BusinessProfile)
}