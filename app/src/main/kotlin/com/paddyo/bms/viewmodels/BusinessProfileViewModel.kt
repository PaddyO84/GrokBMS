package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.entities.BusinessProfile
import com.paddyo.bms.data.entities.BusinessProfileDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessProfileViewModel @Inject constructor(
    private val businessProfileDao: BusinessProfileDao
) : ViewModel() {

    private val _businessProfile = MutableStateFlow<BusinessProfile?>(null)
    val businessProfile: StateFlow<BusinessProfile?> = _businessProfile

    init {
        loadBusinessProfile()
    }

    private fun loadBusinessProfile() {
        viewModelScope.launch {
            businessProfileDao.getBusinessProfile(1).collect { profile ->
                _businessProfile.value = profile
            }
        }
    }

    fun updateBusinessProfile(profile: BusinessProfile) {
        viewModelScope.launch {
            businessProfileDao.update(profile)
        }
    }
}
