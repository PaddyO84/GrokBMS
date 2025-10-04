package com.paddyo.bms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paddyo.bms.data.dao.CustomerDao
import com.paddyo.bms.data.entities.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(private val customerDao: CustomerDao) : ViewModel() {
    val customers: Flow<List<Customer>> = customerDao.getAllCustomers()
    fun insertCustomer(customer: Customer) = viewModelScope.launch { customerDao.insert(customer) }
    fun updateCustomer(customer: Customer) = viewModelScope.launch { customerDao.update(customer) }
    fun deleteCustomer(customer: Customer) = viewModelScope.launch { customerDao.delete(customer) }
    fun getCustomerById(id: Long): Flow<Customer> = customerDao.getCustomerById(id)
}