package com.paddyo.bms.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paddyo.bms.viewmodels.JobViewModel

@Composable
fun JobDetailScreen(navController: NavController, jobId: Long?, viewModel: JobViewModel = hiltViewModel()) {
    val job by viewModel.selectedJob.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (job != null) {
                Text(text = job!!.description, style = MaterialTheme.typography.titleLarge)
                Text(text = "Status: ${job!!.status}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Requested: ${job!!.dateRequested}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Labour Costs: ${job!!.labourCosts}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Material Costs: ${job!!.materialCosts}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* TODO: Navigate to add task */ }) {
                    Text("Add Task")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* TODO: Navigate to add quote */ }) {
                    Text("Add Quote")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* TODO: Navigate to add invoice */ }) {
                    Text("Add Invoice")
                }
            } else {
                Text("Loading job details...")
            }
        }
    }
}
