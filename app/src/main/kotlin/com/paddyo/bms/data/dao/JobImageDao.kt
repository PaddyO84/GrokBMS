package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Delete
    suspend fun insert(image: JobImage)

    @Update
    suspend fun update(image: JobImage)

    @Delete
    suspend fun delete(image: JobImage)
}