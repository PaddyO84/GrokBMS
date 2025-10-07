package com.paddyo.bms.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.paddyo.bms.data.entities.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM Customer")
    fun getAllCustomers(): Flow<List<Customer>>

    @Insert
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Query("DELETE FROM Customer WHERE id = :id")
    suspend fun deleteCustomer(id: Long)
}