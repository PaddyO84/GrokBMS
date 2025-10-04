package com.paddyo.bms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMSApp()
        }
    }
}

data class NavItem(val route: String, val label: String, val icon: ImageVector)

@Composable
fun BMSApp() {
    val navController = rememberNavController()
    val navItems = listOf(
        NavItem("home", "Home", Icons.Filled.Home),
        NavItem("jobs", "Jobs", Icons.Filled.Work),
        NavItem("calendar", "Calendar", Icons.Filled.CalendarToday),
        NavItem("reminders", "Reminders", Icons.Filled.Notifications),
        NavItem("settings", "Settings", Icons.Filled.Settings)
    )

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("BMS") },
                    actions = {
                        if (navController.currentBackStackEntryAsState().value?.destination?.route == "home") {
                            IconButton(onClick = { /* TODO: Implement search */ }) {
                                Icon(Icons.Filled.Search, contentDescription = "Search Customers")
                            }
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    navItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    item.icon,
                                    contentDescription = item.label,
                                    tint = if (currentDestination?.hierarchy?.any { it.route == item.route } == true)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            AnimatedContent(
                targetState = navController.currentBackStackEntryAsState().value?.destination?.route,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                modifier = Modifier.padding(innerPadding)
            ) { targetRoute ->
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") { HomeScreen(navController) }
                    composable("jobs") { JobsScreen() }
                    composable("calendar") { CalendarScreen() }
                    composable("reminders") { RemindersScreen() }
                    composable("settings") { SettingsScreen() }
                    composable("add_customer") { AddCustomerScreen(navController) }
                    composable("customer_details/{customerId}") { backStackEntry ->
                        CustomerDetailsScreen(
                            customerId = backStackEntry.arguments?.getString("customerId")?.toIntOrNull() ?: 0,
                            navController = navController
                        )
                    }
                    composable("create_job/{customerId}") { backStackEntry ->
                        CreateJobScreen(
                            customerId = backStackEntry.arguments?.getString("customerId")?.toIntOrNull() ?: 0,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
