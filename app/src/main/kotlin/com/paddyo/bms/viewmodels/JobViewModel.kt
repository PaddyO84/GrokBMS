package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.JobDao
import com.paddyo.bms.data.TaskDao
import com.paddyo.bms.data.entities.Job
import com.paddyo.bms.data.entities.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val jobDao: JobDao,
    private val taskDao: TaskDao
) : ViewModel() {
    fun getJobsForCustomer(customerId: Long): Flow<List<Job>> {
        return jobDao.getJobsForCustomer(customerId)
    }

    fun getTasksForJob(jobId: Long): Flow<List<Task>> {
        return taskDao.getTasksForJob(jobId)
    }

    fun addJob(job: Job) {
        viewModelScope.launch {
            jobDao.insertJob(job)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskDao.insertTask(task)
        }
    }

    fun updateJob(job: Job) {
        viewModelScope.launch {
            jobDao.updateJob(job)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

    fun deleteJob(id: Long) {
        viewModelScope.launch {
            jobDao.deleteJob(id)
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            taskDao.deleteTask(id)
        }
    }
}