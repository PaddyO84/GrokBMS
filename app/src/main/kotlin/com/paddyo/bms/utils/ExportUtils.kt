package com.paddyo.bms.utils

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.paddyo.bms.data.AppDatabase
import com.paddyo.bms.data.entities.*
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object ExportUtils {
    suspend fun exportDataToCsv(context: Context, database: AppDatabase, backupLocation: String) {
        withContext(Dispatchers.IO) {
            val customers = database.customerDao().getAllCustomers()
            val jobs = database.jobDao().getAllJobs()
            val tasks = database.taskDao().getAllTasks()
            val quotes = database.quoteDao().getAllQuotes()
            val invoices = database.invoiceDao().getAllInvoices()
            val jobImages = database.jobImageDao().getAllImages()

            val csvFile = File(backupLocation, "bms_backup_${System.currentTimeMillis()}.csv")
            csvFile.parentFile?.mkdirs()
            csvFile.bufferedWriter().use { writer ->
                writer.write("Table,Data\n")
                writer.write("Customers,\n")
                customers.forEach { customer ->
                    writer.write("Customer,${customer.id},${customer.name},${customer.companyName ?: ""},${customer.email},${customer.phoneNumbers.joinToString(";")},${customer.roleTitle ?: ""}\n")
                }
                writer.write("Jobs,\n")
                jobs.forEach { job ->
                    writer.write("Job,${job.id},${job.customerId},${job.description},${job.dateRequested},${job.status},${job.labourCosts},${job.materialCosts}\n")
                }
                writer.write("Tasks,\n")
                tasks.forEach { task ->
                    writer.write("Task,${task.id},${task.jobId},${task.description},${task.requiredDate},${task.status}\n")
                }
                writer.write("Quotes,\n")
                quotes.forEach { quote ->
                    writer.write("Quote,${quote.id},${quote.jobId},${quote.amount},${quote.dateIssued}\n")
                }
                writer.write("Invoices,\n")
                invoices.forEach { invoice ->
                    writer.write("Invoice,${invoice.id},${invoice.jobId},${invoice.amount},${invoice.dateIssued}\n")
                }
                writer.write("JobImages,\n")
                jobImages.forEach { image ->
                    writer.write("JobImage,${image.id},${image.jobId},${image.imageUri},${image.description ?: ""}\n")
                }
            }

            val workRequest = OneTimeWorkRequestBuilder<BackupWorker>()
                .setInputData(workDataOf("backup_path" to csvFile.absolutePath))
                .build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }

    suspend fun generateQuotePdf(context: Context, quote: Quote, job: Job, customer: Customer, businessProfile: BusinessProfile?, outputPath: String) {
        withContext(Dispatchers.IO) {
            val pdfFile = File(outputPath, "quote_${quote.id}_${System.currentTimeMillis()}.pdf")
            pdfFile.parentFile?.mkdirs()
            val writer = PdfWriter(pdfFile)
            val pdf = PdfDocument(writer)
            val document = Document(pdf)
            document.add(Paragraph("Quote for ${customer.name}"))
            document.add(Paragraph("Company: ${businessProfile?.companyName ?: "N/A"}"))
            document.add(Paragraph("Job Description: ${job.description}"))
            document.add(Paragraph("Quote Amount: ${quote.amount}"))
            document.add(Paragraph("Date Issued: ${quote.dateIssued}"))
            document.close()
        }
    }

    suspend fun generateInvoicePdf(context: Context, invoice: Invoice, job: Job, customer: Customer, businessProfile: BusinessProfile?, outputPath: String) {
        withContext(Dispatchers.IO) {
            val pdfFile = File(outputPath, "invoice_${invoice.id}_${System.currentTimeMillis()}.pdf")
            pdfFile.parentFile?.mkdirs()
            val writer = PdfWriter(pdfFile)
            val pdf = PdfDocument(writer)
            val document = Document(pdf)
            document.add(Paragraph("Invoice for ${customer.name}"))
            document.add(Paragraph("Company: ${businessProfile?.companyName ?: "N/A"}"))
            document.add(Paragraph("Job Description: ${job.description}"))
            document.add(Paragraph("Invoice Amount: ${invoice.amount}"))
            document.add(Paragraph("Date Issued: ${invoice.dateIssued}"))
            document.close()
        }
    }
}
