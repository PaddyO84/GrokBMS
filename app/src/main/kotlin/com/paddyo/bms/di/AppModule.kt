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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "bms_database").build()
    @Provides
    fun provideCustomerDao(db: AppDatabase) = db.customerDao()
    @Provides
    fun provideJobDao(db: AppDatabase) = db.jobDao()
    @Provides
    fun provideTaskDao(db: AppDatabase) = db.taskDao()
    @Provides
    fun provideReceiptDao(db: AppDatabase) = db.receiptDao()
    @Provides
    fun provideJobImageDao(db: AppDatabase) = db.jobImageDao()
    @Provides
    fun provideInvoiceDao(db: AppDatabase) = db.invoiceDao()
    @Provides
    fun provideQuoteDao(db: AppDatabase) = db.quoteDao()
    @Provides
    fun provideBusinessProfileDao(db: AppDatabase) = db.businessProfileDao()
    @Provides
    fun provideSettingsDao(db: AppDatabase) = db.settingsDao()
}