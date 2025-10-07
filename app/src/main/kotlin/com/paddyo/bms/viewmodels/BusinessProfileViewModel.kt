package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.BusinessProfileDao
import com.paddyo.bms.data.entities.BusinessProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessProfileViewModel @Inject constructor(
    private val businessProfileDao: BusinessProfileDao
) : ViewModel() {
    val businessProfile: Flow<BusinessProfile?> = businessProfileDao.getBusinessProfile()

    fun saveBusinessProfile(profile: BusinessProfile) {
        viewModelScope.launch {
            businessProfileDao.insertBusinessProfile(profile)
        }
    }

    fun updateBusinessProfile(profile: BusinessProfile) {
        viewModelScope.launch {
            businessProfileDao.updateBusinessProfile(profile)
        }
    }
}