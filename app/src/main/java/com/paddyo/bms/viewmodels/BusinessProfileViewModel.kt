package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.dao.BusinessProfileDao
import com.paddyo.bms.data.entities.BusinessProfile
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
        viewModelScope.launch {
            businessProfileDao.getBusinessProfile().collect { profile ->
                _businessProfile.value = profile
            }
        }
    }

    fun saveBusinessProfile(
        companyName: String,
        companyAddress: String,
        companyPhone: String,
        companyEmail: String,
        vatNumber: String?,
        logoUri: String?
    ) {
        viewModelScope.launch {
            val profile = BusinessProfile(
                id = _businessProfile.value?.id ?: 0,
                companyName = companyName,
                companyAddress = companyAddress,
                companyPhone = companyPhone,
                companyEmail = companyEmail,
                vatNumber = vatNumber,
                logoUri = logoUri
            )
            if (profile.id == 0) {
                businessProfileDao.insert(profile)
            } else {
                businessProfileDao.update(profile)
            }
        }
    }
}
