package com.paddyo.bms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paddyo.bms.ui.*
import com.paddyo.bms.ui.theme.BmsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BmsApp()
        }
    }
}

@Composable
fun BmsApp() {
    BmsTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Jobs") },
                        label = { Text("Jobs") },
                        selected = currentRoute == "jobs",
                        onClick = { navController.navigate("jobs") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Calendar") },
                        label = { Text("Calendar") },
                        selected = currentRoute == "calendar",
                        onClick = { navController.navigate("calendar") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Notifications, contentDescription = "Reminders") },
                        label = { Text("Reminders") },
                        selected = currentRoute == "reminders",
                        onClick = { navController.navigate("reminders") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        selected = currentRoute == "settings",
                        onClick = { navController.navigate("settings") }
                    )
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "jobs",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                composable("jobs") { JobsScreen(navController) }
                composable("add_job/{customerId}") { backStackEntry ->
                    val customerId = backStackEntry.arguments?.getString("customerId")?.toLongOrNull()
                    AddJobScreen(navController, customerId)
                }
                composable("job_detail/{jobId}") { backStackEntry ->
                    val jobId = backStackEntry.arguments?.getString("jobId")?.toLongOrNull()
                    JobDetailScreen(navController, jobId)
                }
                composable("calendar") { CalendarScreen(navController) }
                composable("reminders") { RemindersScreen(navController) }
                composable("settings") { SettingsScreen(navController) }
                composable("customers") { CustomersScreen(navController) }
                composable("add_customer") { AddCustomerScreen(navController) }
            }
        }
    }
}

@Composable
fun CalendarScreen(navController: NavController) {
    Text("Calendar Screen - TODO", modifier = Modifier.fillMaxSize())
}

@Composable
fun RemindersScreen(navController: NavController) {
    Text("Reminders Screen - TODO", modifier = Modifier.fillMaxSize())
}

@Composable
fun SettingsScreen(navController: NavController) {
    Text("Settings Screen - TODO", modifier = Modifier.fillMaxSize())
}
