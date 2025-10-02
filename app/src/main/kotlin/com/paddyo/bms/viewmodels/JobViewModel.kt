package com.paddyo.bms.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.dao.JobDao
import com.paddyo.bms.data.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class JobViewModel @Inject constructor(private val jobDao: JobDao) : ViewModel() {
    fun getJobsForCustomer(customerId: Long): Flow<List<Job>> = jobDao.getJobsForCustomer(customerId)
    fun getAllJobs(): Flow<List<Job>> = jobDao.getAllJobs()
    fun insertJob(job: Job) = viewModelScope.launch { jobDao.insert(job) }
    fun updateJob(job: Job) = viewModelScope.launch { jobDao.update(job) }
    fun deleteJob(job: Job) = viewModelScope.launch { jobDao.delete(job) }
    fun getJobById(id: Long): Flow<Job> = jobDao.getJobById(id)
}