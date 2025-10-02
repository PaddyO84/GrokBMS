package com.paddyo.bms.data.dao
import androidx.room.*
import com.paddyo.bms.data.entities.Job
import kotlinx.coroutines.flow.Flow
@Dao
interface JobDao {
    @Query("SELECT * FROM jobs WHERE customerId = :customerId")
    fun getJobsForCustomer(customerId: Long): Flow<List<Job>>
    @Insert
    suspend fun insert(job: Job)
    @Update
    suspend fun update(job: Job)
    @Delete
    suspend fun delete(job: Job)
    @Query("SELECT * FROM jobs WHERE id = :id")
    fun getJobById(id: Long): Flow<Job>
    @Query("SELECT * FROM jobs")
    fun getAllJobs(): Flow<List<Job>>
}