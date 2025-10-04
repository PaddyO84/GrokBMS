package com.paddyo.bms

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CreateJobScreen(customerId: Int, navController: NavController) {
    // Placeholder state (replace with Room database)
    var description by remember { mutableStateOf("") }
    var dateRequested by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var vendors by remember { mutableStateOf("") }
    var laborItems by remember { mutableStateOf(listOf<LaborItem>()) }
    var materialItems by remember { mutableStateOf(listOf<MaterialItem>()) }
    var receiptImages by remember { mutableStateOf(listOf<String>()) }
    var workImages by remember { mutableStateOf(listOf<String>()) }

    // Image picker launchers
    val receiptPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.toString()?.let { receiptImages = receiptImages + it }
    }
    val workImagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.toString()?.let { workImages = workImages + it }
    }

    // Date formatter for display
    val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    // Date picker dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) { Text("Cancel") }
            }
        ) {
            DatePicker(
                state = rememberDatePickerState(
                    initialSelectedDateMillis = dateRequested
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
                ),
                modifier = Modifier.padding(16.dp),
                title = { Text("Select Date Requested") }
            ) { selectedDateMillis ->
                selectedDateMillis?.let {
                    dateRequested = Instant.ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Job for Customer $customerId") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Job Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = dateRequested.format(dateFormatter),
                    onValueChange = { /* Read-only, use date picker */ },
                    label = { Text("Date Requested") },
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Filled.DateRange, contentDescription = "Pick Date")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    readOnly = true
                )
            }
            item {
                OutlinedTextField(
                    value = vendors,
                    onValueChange = { vendors = it },
                    label = { Text("Vendors Engaged (comma-separated)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Text(
                    text = "Labor Costs",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                laborItems.forEachIndexed { index, labor ->
                    LaborItemRow(labor) { /* TODO: Edit labor item */ }
                }
                Button(
                    onClick = {
                        laborItems = laborItems + LaborItem("Labor ${laborItems.size + 1}", 0.0, 0)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Labor Item")
                }
            }
            item {
                Text(
                    text = "Material Costs",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                materialItems.forEachIndexed { index, material ->
                    MaterialItemRow(material) { /* TODO: Edit material item */ }
                }
                Button(
                    onClick = {
                        materialItems = materialItems + MaterialItem("Material ${materialItems.size + 1}", 0.0)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Material Item")
                }
            }
            item {
                Text(
                    text = "Receipt Images",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                AnimatedVisibility(visible = receiptImages.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(receiptImages) { uri ->
                            Text(
                                text = uri.takeLast(20), // Show partial URI for brevity
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { /* TODO: View image */ }
                            )
                        }
                    }
                }
                Button(
                    onClick = { receiptPicker.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Receipt Image")
                }
            }
            item {
                Text(
                    text = "Work Images",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                AnimatedVisibility(visible = workImages.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(workImages) { uri ->
                            Text(
                                text = uri.takeLast(20), // Show partial URI for brevity
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { /* TODO: View image */ }
                            )
                        }
                    }
                }
                Button(
                    onClick = { workImagePicker.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Work Image")
                }
            }
            item {
                Button(
                    onClick = {
                        // TODO: Save job to Room database
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = description.isNotBlank() && dateRequested != null
                ) {
                    Text("Save Job")
                }
            }
        }
    }
}

data class LaborItem(val type: String, val cost: Double, val hours: Int)
data class MaterialItem(val type: String, val cost: Double)

@Composable
fun LaborItemRow(labor: LaborItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = labor.type, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "$${labor.cost} (${labor.hours}h)",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun MaterialItemRow(material: MaterialItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = material.type, style = MaterialTheme.typography.bodyMedium)
            Text(text = "$${material.cost}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
