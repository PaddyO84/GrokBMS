package com.paddyo.bms.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paddyo.bms.ui.theme.BMSTheme
import com.paddyo.bms.viewmodels.SettingsViewModel

@Composable
fun MainScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val settings by viewModel.settings.collectAsState(initial = Settings())
    BMSTheme(darkTheme = settings.theme == "Dark") {
        Scaffold(
            bottomBar = {
                BottomAppBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Work, "Jobs") },
                        label = { Text("Jobs") },
                        selected = navController.currentDestination?.route == "jobs",
                        onClick = { navController.navigate("jobs") { popUpTo("jobs") { inclusive = true } } }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.CalendarToday, "Calendar") },
                        label = { Text("Calendar") },
                        selected = navController.currentDestination?.route == "calendar",
                        onClick = { navController.navigate("calendar") { popUpTo("calendar") { inclusive = true } } }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Notifications, "Reminders") },
                        label = { Text("Reminders") },
                        selected = navController.currentDestination?.route == "reminders",
                        onClick = { navController.navigate("reminders") { popUpTo("reminders") { inclusive = true } } }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Settings, "Settings") },
                        label = { Text("Settings") },
                        selected = navController.currentDestination?.route == "settings",
                        onClick = { navController.navigate("settings") { popUpTo("settings") { inclusive = true } } }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(navController, startDestination = "jobs", Modifier.padding(innerPadding)) {
                composable("jobs") { JobsScreen(navController) }
                composable("calendar") { CalendarScreen(navController) }
                composable("reminders") { RemindersScreen(navController) }
                composable("settings") { SettingsScreen() }
                composable("customer/{customerId}") { backStackEntry ->
                    CustomerDetailScreen(backStackEntry.arguments?.getString("customerId")?.toLongOrNull() ?: 0, navController)
                }
                composable("job/{jobId}") { backStackEntry ->
                    JobDetailScreen(backStackEntry.arguments?.getString("jobId")?.toLongOrNull() ?: 0, navController)
                }
            }
        }
    }
}
