package com.paddyo.bms.data.dao
import androidx.room.*
import com.paddyo.bms.data.entities.Customer
import kotlinx.coroutines.flow.Flow
@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers")
    fun getAllCustomers(): Flow<List<Customer>>
    @Insert
    suspend fun insert(customer: Customer)
    @Update
    suspend fun update(customer: Customer)
    @Delete
    suspend fun delete(customer: Customer)
    @Query("SELECT * FROM customers WHERE id = :id")
    fun getCustomerById(id: Long): Flow<Customer>
}