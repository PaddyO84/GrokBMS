package com.paddyo.bms.data.dao

import androidx.room.Dao
    suspend fun insert(settings: Settings)

    @Update
    suspend fun update(settings: Settings)
}