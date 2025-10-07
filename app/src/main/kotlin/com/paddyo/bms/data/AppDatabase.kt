package com.paddyo.bms.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paddyo.bms.data.converters.ListConverter
import com.paddyo.bms.data.entities.*

@Database(entities = [Customer::class, Job::class, Task::class, BusinessProfile::class], version = 2)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun jobDao(): JobDao
    abstract fun taskDao(): TaskDao
    abstract fun businessProfileDao(): BusinessProfileDao
}