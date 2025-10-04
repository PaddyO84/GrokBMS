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
import com.paddyo.bms.data.entities.Customer
import com.paddyo.bms.viewmodels.CustomerViewModel

@Composable
fun CustomersScreen(navController: NavController, viewModel: CustomerViewModel = hiltViewModel()) {
    val customers by viewModel.customers.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_customer") }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(customers) { customer ->
                CustomerItem(customer = customer, onClick = {
                    navController.navigate("jobs/${customer.id}")
                })
            }
        }
    }
}

@Composable
fun CustomerItem(customer: Customer, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = customer.name, style = MaterialTheme.typography.titleMedium)
            customer.companyName?.let {
                Text(text = "Company: $it", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = "Email: ${customer.email}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
