package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.dao.TaskDao
import com.paddyo.bms.data.entities.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel() {
    fun getTasksForJob(jobId: Long): Flow<List<Task>> = taskDao.getTasksForJob(jobId)
    fun insertTask(task: Task) = viewModelScope.launch { taskDao.insert(task) }
    fun updateTask(task: Task) = viewModelScope.launch { taskDao.update(task) }
    fun deleteTask(task: Task) = viewModelScope.launch { taskDao.delete(task) }
    suspend fun getTasksForDateRange(start: Long, end: Long): List<Task> = taskDao.getTasksForYear(start, end)
}