package com.paddyo.bms

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CustomerDetailsScreen(customerId: Int, navController: NavController) {
    // Placeholder customer data (replace with Room database query)
    val customer = Customer(
        id = customerId,
        name = "John Doe",
        companyName = "Doe Enterprises",
        email = "john@doe.com",
        phone = "123-456-7890",
        roleTitle = "Owner"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(customer.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Company: ${customer.companyName}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Email: ${customer.email}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Phone: ${customer.phone}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Role: ${customer.roleTitle}",
                style = MaterialTheme.typography.bodyLarge
            )
            Button(
                onClick = { navController.navigate("create_job/$customerId") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Start New Job")
            }
        }
    }
}
