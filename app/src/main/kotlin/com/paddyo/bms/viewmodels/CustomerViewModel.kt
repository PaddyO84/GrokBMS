package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.CustomerDao
import com.paddyo.bms.data.entities.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerDao: CustomerDao
) : ViewModel() {
    val customers: Flow<List<Customer>> = customerDao.getAllCustomers()

    fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            customerDao.insertCustomer(customer)
        }
    }

    fun updateCustomer(customer: Customer) {
        viewModelScope.launch {
            customerDao.updateCustomer(customer)
        }
    }

    fun deleteCustomer(id: Long) {
        viewModelScope.launch {
            customerDao.deleteCustomer(id)
        }
    }
}