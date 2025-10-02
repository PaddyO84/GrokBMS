package com.paddyo.bms.data.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paddyo.bms.data.entities.JobImage
import kotlinx.coroutines.flow.Flow
@Dao
interface JobImageDao {
    @Query("SELECT * FROM job_images WHERE jobId = :jobId")
    fun getImagesForJob(jobId: Long): Flow<List<JobImage>>
    @Insert
    suspend fun insert(image: JobImage)
    @Update
    suspend fun update(image: JobImage)
    @Delete
    suspend fun delete(image: JobImage)
}