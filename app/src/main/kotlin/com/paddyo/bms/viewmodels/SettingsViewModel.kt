package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    // Placeholder for backup location and theme settings
    fun setBackupLocation(uri: String) {
        viewModelScope.launch {
            // TODO: Implement backup logic
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            // TODO: Implement theme toggle
        }
    }
}