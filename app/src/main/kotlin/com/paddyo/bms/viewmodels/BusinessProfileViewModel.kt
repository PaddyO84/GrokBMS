package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.entities.BusinessProfile
import com.paddyo.bms.data.dao.BusinessProfileDao
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

    fun saveBusinessProfile(
        companyName: String,
        companyAddress: String,
        companyPhone: String,
        companyEmail: String,
        companyVatNumber: String?,
        companyLogoUri: String?
    ) {
        viewModelScope.launch {
            val profile = BusinessProfile(
                companyName = companyName,
                companyAddress = companyAddress,
                companyPhone = companyPhone,
                companyEmail = companyEmail,
                companyVatNumber = companyVatNumber,
                companyLogoUri = companyLogoUri
            )
            businessProfileDao.insert(profile)
            _businessProfile.value = profile
        }
    }

    fun loadBusinessProfile() {
        viewModelScope.launch {
            _businessProfile.value = businessProfileDao.getBusinessProfile()
        }
    }
}
