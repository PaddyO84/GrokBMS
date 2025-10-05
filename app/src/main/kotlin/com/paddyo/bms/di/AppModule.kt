package com.paddyo.bms.di

import android.content.Context
import com.paddyo.bms.data.entities.AppDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return com.paddyo.bms.data.entities.DatabaseProvider.getDatabase(context)
    }
}
