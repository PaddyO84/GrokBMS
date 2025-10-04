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

    init {
        viewModelScope.launch {
            settingsDao.getSettings().collect { settings ->
                _settings.value = settings
            }
        }
    }

    fun saveSettings(backupLocation: String, darkMode: Boolean, reminderDaysBefore: Int) {
        viewModelScope.launch {
            val settings = Settings(
                id = _settings.value?.id ?: 0,
                backupLocation = backupLocation,
                darkMode = darkMode,
                reminderDaysBefore = reminderDaysBefore
            )
            if (settings.id == 0) {
                settingsDao.insert(settings)
            } else {
                settingsDao.update(settings)
            }
        }
    }
}
