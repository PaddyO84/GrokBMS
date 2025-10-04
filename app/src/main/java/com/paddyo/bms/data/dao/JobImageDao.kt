package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paddyo.bms.data.entities.JobImage
import kotlinx.coroutines.flow.Flow

@Dao
interface JobImageDao {
    @Insert
    suspend fun insert(jobImage: JobImage)

    @Query("SELECT * FROM job_images WHERE jobId = :jobId")
    fun getImagesByJob(jobId: Int): Flow<List<JobImage>>
}
