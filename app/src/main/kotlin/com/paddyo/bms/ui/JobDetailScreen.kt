package com.paddyo.bms.ui
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.paddyo.bms.data.entities.Invoice
import com.paddyo.bms.data.entities.Quote
import com.paddyo.bms.data.entities.Task
import com.paddyo.bms.utils.saveImage
import com.paddyo.bms.viewmodels.InvoiceViewModel
import com.paddyo.bms.viewmodels.JobViewModel
import com.paddyo.bms.viewmodels.QuoteViewModel
import com.paddyo.bms.viewmodels.TaskViewModel
@Composable
fun JobDetailScreen(jobId: Long, navController: NavController, jobViewModel: JobViewModel = hiltViewModel(), taskViewModel: TaskViewModel = hiltViewModel(), invoiceViewModel: InvoiceViewModel = hiltViewModel(), quoteViewModel: QuoteViewModel = hiltViewModel()) {
    val job by jobViewModel.getJobById(jobId).collectAsState(initial = null)
    val tasks by taskViewModel.getTasksForJob(jobId).collectAsState(initial = emptyList())
    val invoices by invoiceViewModel.getInvoicesForJob(jobId).collectAsState(initial = emptyList())
    val quotes by quoteViewModel.getQuotesForJob(jobId).collectAsState(initial = emptyList())
    var showAddTaskDialog by rememberSaveable { mutableStateOf(false) }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { saveImage(navController.context, it) } // Placeholder for image saving
    }
    job?.let { j ->
        Column(Modifier.padding(16.dp)) {
            Text(j.title, style = MaterialTheme.typography.headlineSmall)
            Text("Status: ${j.status}", style = MaterialTheme.typography.bodyMedium)
            Text("Requested: ${java.util.Date(j.dateRequested)}", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { showAddTaskDialog = true }) { Text("Add Task") }
            Button(onClick = { imagePicker.launch("image/*") }) { Text("Add Image") }
            Button(onClick = { quoteViewModel.createQuote(jobId, j.customerId) }) { Text("Generate Quote") }
            Button(onClick = { invoiceViewModel.createInvoice(jobId, j.customerId) }) { Text("Generate Invoice") }
            Spacer(Modifier.height(16.dp))
            Text("Tasks", style = MaterialTheme.typography.titleLarge)
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(task)
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("Quotes", style = MaterialTheme.typography.titleLarge)
            LazyColumn {
                items(quotes) { quote ->
                    QuoteItem(quote)
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("Invoices", style = MaterialTheme.typography.titleLarge)
            LazyColumn {
                items(invoices) { invoice ->
                    InvoiceItem(invoice)
                }
            }
        }
        if (showAddTaskDialog) {
            AddTaskDialog(
                onDismiss = { showAddTaskDialog = false },
                onSave = { description, laborCost, laborType, materialCost, materialType ->
                    taskViewModel.insertTask(Task(
                        jobId = jobId,
                        description = description,
                        requiredDate = System.currentTimeMillis() + 86_400_000,
                        vendor = null,
                        laborCost = laborCost.toDoubleOrNull() ?: 0.0,
                        laborType = laborType,
                        materialCost = materialCost.toDoubleOrNull() ?: 0.0,
                        materialType = materialType
                    ))
                    showAddTaskDialog = false
                }
            )
        }
    } ?: Text("Loading...")
}
@Composable
fun TaskItem(task: Task) {
    Card(Modifier.fillMaxWidth().padding(8.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(task.description, style = MaterialTheme.typography.titleMedium)
            Text("Labor: ${task.laborCost} (${task.laborType})", style = MaterialTheme.typography.bodyMedium)
            Text("Material: ${task.materialCost} (${task.materialType})", style = MaterialTheme.typography.bodyMedium)
            Text("Due: ${java.util.Date(task.requiredDate)}", style = MaterialTheme.typography.bodySmall)
            task.vendor?.let { Text("Vendor: $it", style = MaterialTheme.typography.bodySmall) }
        }
    }
}
@Composable
fun InvoiceItem(invoice: Invoice) {
    Card(Modifier.fillMaxWidth().padding(8.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text("Invoice #${invoice.id}", style = MaterialTheme.typography.titleMedium)
            Text("Total: ${invoice.totalAmount}", style = MaterialTheme.typography.bodyMedium)
            Text("Status: ${invoice.status}", style = MaterialTheme.typography.bodyMedium)
            Text("Issued: ${java.util.Date(invoice.issueDate)}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
@Composable
fun QuoteItem(quote: Quote) {
    Card(Modifier.fillMaxWidth().padding(8.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text("Quote #${quote.id}", style = MaterialTheme.typography.titleMedium)
            Text("Total: ${quote.totalAmount}", style = MaterialTheme.typography.bodyMedium)
            Text("Status: ${quote.status}", style = MaterialTheme.typography.bodyMedium)
            Text("Issued: ${java.util.Date(quote.issueDate)}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
@Composable
fun AddTaskDialog(onDismiss: () -> Unit, onSave: (String, String, String, String, String) -> Unit) {
    var description by rememberSaveable { mutableStateOf("") }
    var laborCost by rememberSaveable { mutableStateOf("") }
    var laborType by rememberSaveable { mutableStateOf("") }
    var materialCost by rememberSaveable { mutableStateOf("") }
    var materialType by rememberSaveable { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Task") },
        text = {
            Column {
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(value = laborCost, onValueChange = { laborCost = it }, label = { Text("Labor Cost") })
                TextField(value = laborType, onValueChange = { laborType = it }, label = { Text("Labor Type") })
                TextField(value = materialCost, onValueChange = { materialCost = it }, label = { Text("Material Cost") })
                TextField(value = materialType, onValueChange = { materialType = it }, label = { Text("Material Type") })
            }
        },
        confirmButton = { Button(onClick = { onSave(description, laborCost, laborType, materialCost, materialType) }) { Text("Save") } },
        dismissButton = { Button(onClick = onDismiss) { Text("Cancel") } }
    )
}