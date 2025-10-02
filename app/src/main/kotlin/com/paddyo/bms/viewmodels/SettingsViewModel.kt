package com.paddyo.bms.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.paddyo.bms.data.dao.SettingsDao
import com.paddyo.bms.data.entities.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDao: SettingsDao,
    private val workManager: WorkManager
) : ViewModel() {
    val settings: Flow<Settings> = settingsDao.getSettings()
    fun updateBackupLocation(location: String) = viewModelScope.launch {
        val current = settingsDao.getSettings().firstOrNull() ?: Settings()
        settingsDao.update(current.copy(backupLocation = location))
        scheduleBackup()
    }
    fun updateTheme(theme: String) = viewModelScope.launch {
        val current = settingsDao.getSettings().firstOrNull() ?: Settings()
        settingsDao.update(current.copy(theme = theme))
    }
    fun updateReminderFrequency(frequency: String) = viewModelScope.launch {
        val current = settingsDao.getSettings().firstOrNull() ?: Settings()
        settingsDao.update(current.copy(reminderFrequency = frequency))
        scheduleReminders()
    }
    private fun scheduleBackup() {
        val request = PeriodicWorkRequestBuilder<BackupWorker>(1, TimeUnit.DAYS).build()
        workManager.enqueueUniquePeriodicWork("backup", ExistingPeriodicWorkPolicy.REPLACE, request)
    }
    private fun scheduleReminders() {
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS).build()
        workManager.enqueueUniquePeriodicWork("reminders", ExistingPeriodicWorkPolicy.REPLACE, request)
    }
}