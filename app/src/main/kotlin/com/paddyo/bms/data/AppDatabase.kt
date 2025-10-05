package com.paddyo.bms.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paddyo.bms.data.dao.*
import com.paddyo.bms.data.entities.*

@Database(
    entities = [
        Customer::class,
        Job::class,
        Task::class,
        Quote::class,
        Invoice::class,
        BusinessProfile::class,
        Settings::class,
        JobImage::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun jobDao(): JobDao
    abstract fun taskDao(): TaskDao
    abstract fun quoteDao(): QuoteDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun businessProfileDao(): BusinessProfileDao
    abstract fun settingsDao(): SettingsDao
    abstract fun jobImageDao(): JobImageDao
}