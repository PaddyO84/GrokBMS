package com.paddyo.bms.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paddyo.bms.data.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE jobId = :jobId")
    fun getTasksForJob(jobId: Long): Flow<List<Task>>

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE requiredDate <= :date")
    suspend fun getUpcomingTasks(date: Long): List<Task>

    @Query("SELECT * FROM tasks WHERE requiredDate BETWEEN :start AND :end")
    suspend fun getTasksForYear(start: Long, end: Long): List<Task>
}