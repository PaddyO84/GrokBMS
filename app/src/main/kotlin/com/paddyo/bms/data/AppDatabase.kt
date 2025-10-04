package com.paddyo.bms.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paddyo.bms.data.converters.Converters
import com.paddyo.bms.data.dao.*
import com.paddyo.bms.data.entities.*

@Database(
    entities = [Customer::class, Job::class, Task::class, Receipt::class, JobImage::class, Invoice::class, Quote::class, BusinessProfile::class, Settings::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun jobDao(): JobDao
    abstract fun taskDao(): TaskDao
    abstract fun receiptDao(): ReceiptDao
    abstract fun jobImageDao(): JobImageDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun quoteDao(): QuoteDao
    abstract fun businessProfileDao(): BusinessProfileDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bms_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}