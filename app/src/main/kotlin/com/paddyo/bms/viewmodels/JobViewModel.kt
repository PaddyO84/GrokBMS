package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.dao.JobDao
import com.paddyo.bms.data.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val jobDao: JobDao
) : ViewModel() {
    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    val jobs: StateFlow<List<Job>> = _jobs.asStateFlow()

    private val _selectedJob = MutableStateFlow<Job?>(null)
    val selectedJob: StateFlow<Job?> = _selectedJob.asStateFlow()

    fun loadJobs() {
        viewModelScope.launch {
            jobDao.getAllJobs().collect { jobList ->
                _jobs.value = jobList
            }
        }
    }

    fun loadJobsForCustomer(customerId: Long) {
        viewModelScope.launch {
            jobDao.getJobsForCustomer(customerId).collect { jobList ->
                _jobs.value = jobList
            }
        }
    }

    fun selectJob(jobId: Long) {
        viewModelScope.launch {
            _selectedJob.value = jobDao.getJobById(jobId)
        }
    }

    fun addJob(
        customerId: Long,
        description: String,
        dateRequested: String,
        status: String,
        labourCosts: Double,
        materialCosts: Double
    ) {
        viewModelScope.launch {
            val job = Job(
                customerId = customerId,
                description = description,
                dateRequested = dateRequested,
                status = status,
                labourCosts = labourCosts,
                materialCosts = materialCosts
            )
            jobDao.insert(job)
        }
    }
}
