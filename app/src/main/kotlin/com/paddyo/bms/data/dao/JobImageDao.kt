package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paddyo.bms.data.entities.JobImage

@Dao
interface JobImageDao {
    @Insert
    suspend fun insert(jobImage: JobImage)

    @Query("SELECT * FROM job_images WHERE jobId = :jobId")
    suspend fun getImagesForJob(jobId: Long): List<JobImage>
}
