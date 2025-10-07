package com.paddyo.bms.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paddyo.bms.data.daos.CustomerDao
import com.paddyo.bms.data.entities.Customer
import com.paddyo.bms.data.entities.Job
import com.paddyo.bms.data.entities.Task

@Database(entities = [Customer::class, Job::class, Task::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
}
