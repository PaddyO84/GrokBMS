package com.paddyo.bms.ui
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paddyo.bms.viewmodels.SettingsViewModel
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val settings by viewModel.settings.collectAsState(initial = Settings())
    var backupLocation by rememberSaveable { mutableStateOf(settings.backupLocation) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        uri?.let { viewModel.updateBackupLocation(it.toString()) }
    }
    Column(Modifier.padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        TextField(
            value = backupLocation,
            onValueChange = { backupLocation = it },
            label = { Text("Backup Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { launcher.launch(null) }) { Text("Select Backup Folder") }
        Spacer(Modifier.height(16.dp))
        Text("Theme", style = MaterialTheme.typography.titleMedium)
        Row {
            RadioButton(
                selected = settings.theme == "Light",
                onClick = { viewModel.updateTheme("Light") }
            )
            Text("Light")
            Spacer(Modifier.width(8.dp))
            RadioButton(
                selected = settings.theme == "Dark",
                onClick = { viewModel.updateTheme("Dark") }
            )
            Text("Dark")
        }
        Spacer(Modifier.height(16.dp))
        Text("Reminder Frequency", style = MaterialTheme.typography.titleMedium)
        Row {
            RadioButton(
                selected = settings.reminderFrequency == "Daily",
                onClick = { viewModel.updateReminderFrequency("Daily") }
            )
            Text("Daily")
            Spacer(Modifier.width(8.dp))
            RadioButton(
                selected = settings.reminderFrequency == "Weekly",
                onClick = { viewModel.updateReminderFrequency("Weekly") }
            )
            Text("Weekly")
        }
    }
}