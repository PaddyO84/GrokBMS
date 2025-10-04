package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.dao.SettingsDao
import com.paddyo.bms.data.entities.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDao: SettingsDao
) : ViewModel() {
    private val _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?> = _settings

    fun saveSettings(backupLocation: String, isDarkMode: Boolean, reminderDays: Int) {
        viewModelScope.launch {
            val settings = Settings(
                backupLocation = backupLocation,
                isDarkMode = isDarkMode,
                reminderDays = reminderDays
            )
            settingsDao.insert(settings)
            _settings.value = settings
        }
    }

    fun loadSettings() {
        viewModelScope.launch {
            _settings.value = settingsDao.getSettings()
        }
    }
}
