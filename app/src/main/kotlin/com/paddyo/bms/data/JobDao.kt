package com.paddyo.bms.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paddyo.bms.data.entities.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM Job WHERE customerId = :customerId")
    fun getJobsForCustomer(customerId: Long): Flow<List<Job>>

    @Insert
    suspend fun insertJob(job: Job)

    @Update
    suspend fun updateJob(job: Job)

    @Query("DELETE FROM Job WHERE id = :id")
    suspend fun deleteJob(id: Long)
}