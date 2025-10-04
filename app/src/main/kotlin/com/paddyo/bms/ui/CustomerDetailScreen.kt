package com.paddyo.bms.ui
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paddyo.bms.data.entities.Job
import com.paddyo.bms.viewmodels.CustomerViewModel
import com.paddyo.bms.viewmodels.JobViewModel
@Composable
fun CustomerDetailScreen(customerId: Long, navController: NavController, viewModel: CustomerViewModel = hiltViewModel(), jobViewModel: JobViewModel = hiltViewModel()) {
    val customer by viewModel.getCustomerById(customerId).collectAsState(initial = null)
    val jobs by jobViewModel.getJobsForCustomer(customerId).collectAsState(initial = emptyList())
    var showAddJobDialog by rememberSaveable { mutableStateOf(false) }
    customer?.let { cust ->
        Column(Modifier.padding(16.dp)) {
            Text(cust.name, style = MaterialTheme.typography.headlineSmall)
            cust.companyName?.let { Text(it, style = MaterialTheme.typography.bodyLarge) }
            Text(cust.email, style = MaterialTheme.typography.bodyMedium)
            Text("Phone: ${cust.phoneNumbers.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { showAddJobDialog = true }) { Text("Add New Job") }
            Spacer(Modifier.height(16.dp))
            Text("Jobs", style = MaterialTheme.typography.titleLarge)
            LazyColumn {
                items(jobs) { job ->
                    JobItem(job) { navController.navigate("job/${job.id}") }
                }
            }
        }
        if (showAddJobDialog) {
            AddJobDialog(
                onDismiss = { showAddJobDialog = false },
                onSave = { title, description ->
                    jobViewModel.insertJob(Job(customerId = customerId, title = title, description = description, dateRequested = System.currentTimeMillis(), status = "Pending"))
                    showAddJobDialog = false
                }
            )
        }
    } ?: Text("Loading...")
}
@Composable
fun JobItem(job: Job, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(8.dp).clickable(onClick = onClick)) {
        Column(Modifier.padding(16.dp)) {
            Text(job.title, style = MaterialTheme.typography.titleMedium)
            Text("Status: ${job.status}", style = MaterialTheme.typography.bodyMedium)
            Text("Requested: ${java.util.Date(job.dateRequested)}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
@Composable
fun AddJobDialog(onDismiss: () -> Unit, onSave: (String, String) -> Unit) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Job") },
        text = {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
            }
        },
        confirmButton = { Button(onClick = { onSave(title, description) }) { Text("Save") } },
        dismissButton = { Button(onClick = onDismiss) { Text("Cancel") } }
    )
}