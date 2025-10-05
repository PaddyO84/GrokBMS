package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.entities.Settings
import com.paddyo.bms.data.entities.SettingsDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
kotlinx.coroutines.flow.MutableStateFlow
kotlinx.coroutines.launch
javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDao: SettingsDao
) : ViewModel() {

    private val _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?> = _settings

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            settingsDao.getSettings(1).collect { settings ->
                _settings.value = settings
            }
        }
    }

    fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            settingsDao.update(settings)
        }
    }
}