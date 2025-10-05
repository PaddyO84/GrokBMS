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
import com.paddyo.bms.data.entities.BusinessProfile
import com.paddyo.bms.viewmodels.AccountsViewModel
import com.paddyo.bms.viewmodels.BusinessProfileViewModel
import com.paddyo.bms.viewmodels.SettingsViewModel
import java.util.Calendar
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    businessProfileViewModel: BusinessProfileViewModel = hiltViewModel(),
    accountsViewModel: AccountsViewModel = hiltViewModel()
) {
    val settings by settingsViewModel.settings.collectAsState(initial = Settings())
    val businessProfile by businessProfileViewModel.businessProfile.collectAsState(initial = BusinessProfile())
    var backupLocation by rememberSaveable { mutableStateOf(settings.backupLocation) }
    var companyName by rememberSaveable { mutableStateOf(businessProfile.companyName) }
    var companyAddress by rememberSaveable { mutableStateOf(businessProfile.companyAddress) }
    var companyPhone by rememberSaveable { mutableStateOf(businessProfile.companyPhone) }
    var companyEmail by rememberSaveable { mutableStateOf(businessProfile.companyEmail) }
    var vatNumber by rememberSaveable { mutableStateOf(businessProfile.vatNumber ?: "") }
    var vatRate by rememberSaveable { mutableStateOf(businessProfile.vatRate.toString()) }
    var selectedYear by rememberSaveable { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    val logoPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { businessProfileViewModel.updateBusinessProfile(businessProfile.copy(logoPath = it.toString())) }
    }
    val folderPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        uri?.let { settingsViewModel.updateBackupLocation(it.toString()) }
    }
    Column(Modifier.padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text("Business Profile", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = { Text("Company Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = companyAddress,
            onValueChange = { companyAddress = it },
            label = { Text("Company Address") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = companyPhone,
            onValueChange = { companyPhone = it },
            label = { Text("Company Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = companyEmail,
            onValueChange = { companyEmail = it },
            label = { Text("Company Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = vatNumber,
            onValueChange = { vatNumber = it },
            label = { Text("VAT Number (Optional") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { logoPicker.launch("image/*") }) { Text("Upload Company Logo") }
        Button(onClick = {
            businessProfileViewModel.updateBusinessProfile(
                businessProfile.copy(
                    companyName = companyName,
                    companyAddress = companyAddress,
                    companyPhone = companyPhone,
                    companyEmail = companyEmail,
                    vatNumber = vatNumber.takeIf { it.isNotBlank() }
                )
            )
        }) { Text("Save Business Profile") }
        Spacer(Modifier.height(16.dp))
        Text("Backup Location", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = backupLocation,
            onValueChange = { backupLocation = it },
            label = { Text("Backup Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { folderPicker.launch(null) }) { Text("Select Backup Folder") }
        Spacer(Modifier.height(16.dp))
        Text("Theme", style = MaterialTheme.typography.titleMedium)
        Row {
            RadioButton(
                selected = settings.theme == "Light",
                onClick = { settingsViewModel.updateTheme("Light") }
            )
            Text("Light")
            Spacer(Modifier.width(8.dp))
            RadioButton(
                selected = settings.theme == "Dark",
                onClick = { settingsViewModel.updateTheme("Dark") }
            )
            Text("Dark")
        }
        Spacer(Modifier.height(16.dp))
        Text("Reminder Frequency", style = MaterialTheme.typography.titleMedium)
        Row {
            RadioButton(
                selected = settings.reminderFrequency == "Daily",
                onClick = { settingsViewModel.updateReminderFrequency("Daily") }
            )
            Text("Daily")
            Spacer(Modifier.width(8.dp))
            RadioButton(
                selected = settings.reminderFrequency == "Weekly",
                onClick = { settingsViewModel.updateReminderFrequency("Weekly") }
            )
            Text("Weekly")
        }
        Spacer(Modifier.height(16.dp))
        Text("Year-End Accounts Export", style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = selectedYear.toString(),
                onValueChange = { selectedYear = it.toIntOrNull() ?: selectedYear },
                label = { Text("Year") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = { accountsViewModel.exportYearEndAccounts(selectedYear) }) {
                Text("Export")
            }
        }
    }
}
