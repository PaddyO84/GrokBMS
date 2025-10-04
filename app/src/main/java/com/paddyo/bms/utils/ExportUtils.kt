package com.paddyo.bms.utils

import android.content.Context
import com.paddyo.bms.data.dao.*
import com.paddyo.bms.data.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ExportUtils {

    suspend fun exportData(
        context: Context,
        backupLocation: String,
        customerDao: CustomerDao,
        jobDao: JobDao,
        receiptDao: ReceiptDao,
        quoteDao: QuoteDao,
        invoiceDao: InvoiceDao
    ) {
        withContext(Dispatchers.IO) {
            val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
            val backupDir = File(backupLocation)
            if (!backupDir.exists()) backupDir.mkdirs()

            // Export customers
            val customersFile = File(backupDir, "customers_$timestamp.csv")
            FileOutputStream(customersFile).use { output ->
                val writer = output.writer()
                writer.write("id,name,companyName,email,phone,roleTitle\n")
                customerDao.getAllCustomers().collect { customers ->
                    customers.forEach { customer ->
                        writer.write("${customer.id},${customer.name},${customer.companyName},${customer.email},${customer.phone},${customer.roleTitle}\n")
                    }
                }
            }

            // Export jobs
            val jobsFile = File(backupDir, "jobs_$timestamp.csv")
            FileOutputStream(jobsFile).use { output ->
                val writer = output.writer()
                writer.write("id,customerId,description,dateRequested,vendors\n")
                jobDao.getJobsByCustomer(0).collect { jobs -> // Note: Need to iterate over all customers
                    jobs.forEach { job ->
                        writer.write("${job.id},${job.customerId},${job.description},${job.dateRequested},${job.vendors}\n")
                    }
                }
            }

            // Export receipts
            val receiptsFile = File(backupDir, "receipts_$timestamp.csv")
            FileOutputStream(receiptsFile).use { output ->
                val writer = output.writer()
                writer.write("id,jobId,imageUri,amount\n")
                receiptDao.getReceiptsByJob(0).collect { receipts -> // Note: Need to iterate over all jobs
                    receipts.forEach { receipt ->
                        writer.write("${receipt.id},${receipt.jobId},${receipt.imageUri},${receipt.amount}\n")
                    }
                }
            }

            // Export quotes
            val quotesFile = File(backupDir, "quotes_$timestamp.csv")
            FileOutputStream(quotesFile).use { output ->
                val writer = output.writer()
                writer.write("id,jobId,amount,createdAt\n")
                quoteDao.getQuotesByJob(0).collect { quotes -> // Note: Need to iterate over all jobs
                    quotes.forEach { quote ->
                        writer.write("${quote.id},${quote.jobId},${quote.amount},${quote.createdAt}\n")
                    }
                }
            }

            // Export invoices
            val invoicesFile = File(backupDir, "invoices_$timestamp.csv")
            FileOutputStream(invoicesFile).use { output ->
                val writer = output.writer()
                writer.write("id,jobId,amount,issuedAt\n")
                invoiceDao.getInvoicesByJob(0).collect { invoices -> // Note: Need to iterate over all jobs
                    invoices.forEach { invoice ->
                        writer.write("${invoice.id},${invoice.jobId},${invoice.amount},${invoice.issuedAt}\n")
                    }
                }
            }
        }
    }
}
