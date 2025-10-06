package com.paddyo.bms.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paddyo.bms.viewmodels.JobViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddJobScreen(
    navController: NavController,
    customerId: Long? = null,
    viewModel: JobViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf("") }
    var labourCosts by remember { mutableStateOf("") }
    var materialCosts by remember { mutableStateOf("") }
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Job") },
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
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Job Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = labourCosts,
                onValueChange = { labourCosts = it },
                label = { Text("Labour Costs") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = materialCosts,
                onValueChange = { materialCosts = it },
                label = { Text("Material Costs") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (description.isNotBlank() && labourCosts.isNotBlank() && materialCosts.isNotBlank()) {
                        viewModel.addJob(
                            customerId = customerId ?: 0L,
                            description = description,
                            dateRequested = currentDate,
                            status = "Pending",
                            labourCosts = labourCosts.toDoubleOrNull() ?: 0.0,
                            materialCosts = materialCosts.toDoubleOrNull() ?: 0.0
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Job")
            }
        }
    }
}
