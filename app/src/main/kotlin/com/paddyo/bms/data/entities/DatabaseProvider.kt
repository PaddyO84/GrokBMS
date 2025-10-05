package com.paddyo.bms.data.entities

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "bms_database"
            )
                .addMigrations(AppDatabase.MIGRATION_1_2)
                .build()
            INSTANCE = instance
            instance
        }
    }
}