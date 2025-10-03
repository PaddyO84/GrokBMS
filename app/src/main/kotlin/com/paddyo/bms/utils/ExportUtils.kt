package com.paddyo.bms.utils

import android.content.Context
import com.paddyo.bms.data.entities.Invoice
import com.paddyo.bms.data.entities.Receipt
import com.paddyo.bms.data.entities.Task
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun generateYearEndAccounts(
    context: Context,
    year: Int,
    invoices: List<Invoice>,
    receipts: List<Receipt>,
    tasks: List<Task>,
    outputPath: String,
    vatRate: Double
) {
    val file = File(context.filesDir, outputPath)
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    file.bufferedWriter().use { writer ->
        // Write CSV header
        writer.write("Type,Date,Description,Amount,Tax,TotalWithTax,Labor Cost,Labor Type,Material Cost,Material Type
")

        // Write Invoices with tax
        invoices.forEach { invoice ->
            val tax = invoice.totalAmount * (vatRate / 100)
            val totalWithTax = invoice.totalAmount + tax
            writer.write("Invoice,${dateFormat.format(Date(invoice.issueDate))},Invoice #${invoice.id},${invoice.totalAmount},${tax},${totalWithTax},,,,
")
        }

        // Write Receipts with tax
        receipts.forEach { receipt ->
            val tax = receipt.amount * (vatRate / 100)
            val totalWithTax = receipt.amount + tax
            writer.write("Receipt,${dateFormat.format(Date(receipt.date))},Receipt #${receipt.id},${receipt.amount},${tax},${totalWithTax},,,,
")
        }

        // Write Tasks with tax
        tasks.forEach { task ->
            val totalCost = task.laborCost + task.materialCost
            val tax = totalCost * (vatRate / 100)
            val totalWithTax = totalCost + tax
            writer.write("Task,${dateFormat.format(Date(task.requiredDate))},${task.description},${totalCost},${tax},${totalWithTax},${task.laborCost},${task.laborType},${task.materialCost},${task.materialType}
")
        }
    }
}