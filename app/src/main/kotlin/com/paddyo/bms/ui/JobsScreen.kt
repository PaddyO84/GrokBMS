package com.paddyo.bms.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paddyo.bms.data.entities.Job
import com.paddyo.bms.viewmodels.JobViewModel

@Composable
fun JobsScreen(
    navController: NavController,
    customerId: Long? = null,
    viewModel: JobViewModel = hiltViewModel()
) {
    val jobs by viewModel.jobs.collectAsState()
    
    // Load jobs based on whether a customerId is provided
    if (customerId != null) {
        viewModel.loadJobsForCustomer(customerId)
    } else {
        viewModel.loadJobs()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add_job${customerId?.let { "/$it" } ?: ""}")
            }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(jobs) { job ->
                JobItem(job = job, onClick = {
                    viewModel.selectJob(job.id)
                    navController.navigate("job_detail/${job.id}")
                })
            }
        }
    }
}

@Composable
fun JobItem(job: Job, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = job.description, style = MaterialTheme.typography.titleMedium)
            Text(text = "Status: ${job.status}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Requested: ${job.dateRequested}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Total Cost: ${(job.labourCosts + job.materialCosts)}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
