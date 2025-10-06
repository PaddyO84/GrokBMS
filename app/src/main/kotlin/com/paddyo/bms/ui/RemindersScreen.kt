package com.paddyo.bms.ui
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paddyo.bms.data.entities.Task
import com.paddyo.bms.viewmodels.TaskViewModel
@Composable
fun RemindersScreen(navController: NavController, viewModel: TaskViewModel = hiltViewModel()) {
    var tasks by remember { mutableStateOf<List<Task>>(emptyList()) }
    LaunchedEffect(Unit) {
        tasks = viewModel.getTasksForDateRange(System.currentTimeMillis(), System.currentTimeMillis() + 30L * 86_400_000) // Next 30 days
    }
    Column(Modifier.padding(16.dp)) {
        Text("Reminders", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        if (tasks.isEmpty()) {
            Text("No upcoming tasks", Modifier.padding(16.dp))
        } else {
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(task, onClick = { navController.navigate("job/${task.jobId}") })
                }
            }
        }
    }
}
