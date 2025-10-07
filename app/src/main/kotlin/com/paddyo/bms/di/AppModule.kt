package com.paddyo.bms.di

import android.content.Context
import androidx.room.Room
import com.paddyo.bms.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCustomerDao(database: AppDatabase) = database.customerDao()

    @Provides
    @Singleton
    fun provideJobDao(database: AppDatabase) = database.jobDao()

    @Provides
    @Singleton
    fun provideTaskDao(database: AppDatabase) = database.taskDao()

    @Provides
    @Singleton
    fun provideBusinessProfileDao(database: AppDatabase) = database.businessProfileDao()
}