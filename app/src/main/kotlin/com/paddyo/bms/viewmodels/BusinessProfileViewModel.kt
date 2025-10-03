package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.dao.BusinessProfileDao
import com.paddyo.bms.data.entities.BusinessProfile
import dagger.hilt.android.lifecycle.HiltViewModel
    kotlinx.coroutines.flow.Flow
    kotlinx.coroutines.launch
    javax.inject.Inject

@HiltViewModel
class BusinessProfileViewModel @Inject constructor(private val businessProfileDao: BusinessProfileDao) : ViewModel() {
    val businessProfile: Flow<BusinessProfile> = businessProfileDao.getBusinessProfile()
    fun updateBusinessProfile(profile: BusinessProfile) = viewModelScope.launch {
        businessProfileDao.update(profile)
    }
}