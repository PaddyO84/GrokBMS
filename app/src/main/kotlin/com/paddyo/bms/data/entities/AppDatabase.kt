package com.paddyo.bms.data.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.paddyo.bms.data.converters.ListConverter

@Database(
    entities = [Customer::class, Job::class, Task::class, Quote::class, Invoice::class, BusinessProfile::class, Settings::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun jobDao(): JobDao
    abstract fun taskDao(): TaskDao
    abstract fun quoteDao(): QuoteDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun businessProfileDao(): BusinessProfileDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `tasks` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `jobId` INTEGER NOT NULL,
                        `description` TEXT NOT NULL,
                        `requiredDate` INTEGER NOT NULL,
                        `vendor` TEXT,
                        `laborCost` REAL,
                        `materialCost` REAL,
                        `receiptImagePaths` TEXT,
                        `workImagePaths` TEXT,
                        FOREIGN KEY(`jobId`) REFERENCES `jobs`(`id`) ON DELETE CASCADE
                    )
                """)
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `business_profile` (
                        `id` INTEGER PRIMARY KEY NOT NULL,
                        `companyName` TEXT NOT NULL,
                        `address` TEXT NOT NULL,
                        `phoneNumber` TEXT NOT NULL,
                        `email` TEXT NOT NULL,
                        `vatNumber` TEXT,
                        `logoPath` TEXT
                    )
                """)
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `settings` (
                        `id` INTEGER PRIMARY KEY NOT NULL,
                        `backupLocation` TEXT,
                        `theme` TEXT NOT NULL,
                        `reminderDaysBefore` INTEGER NOT NULL
                    )
                """)
            }
        }
    }
}