package com.paddyo.bms.utils
import android.content.Context
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Image
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.paddyo.bms.data.entities.*
import java.io.File
fun generateInvoice(
    context: Context,
    invoice: Invoice,
    businessProfile: BusinessProfile,
    customer: Customer,
    tasks: List<Task>,
    outputPath: String
) {
    val pdfWriter = PdfWriter(File(context.filesDir, outputPath).absolutePath)
    val pdfDocument = PdfDocument(pdfWriter)
    val document = Document(pdfDocument)
    // Header: Business Profile
    businessProfile.logoPath?.let { logoPath ->
        try {
            val imageData = ImageDataFactory.create(logoPath)
            val image = Image(imageData).scaleToFit(100f, 100f)
            document.add(image)
        } catch (e: Exception) {
            // Handle invalid logo path
        }
    }
    document.add(Paragraph(businessProfile.companyName).setBold().setFontSize(16f).setTextAlignment(TextAlignment.CENTER))
    document.add(Paragraph(businessProfile.companyAddress).setFontSize(12f))
    document.add(Paragraph("Phone: ${businessProfile.companyPhone}").setFontSize(10f))
    document.add(Paragraph("Email: ${businessProfile.companyEmail}").setFontSize(10f))
    businessProfile.vatNumber?.let { document.add(Paragraph("VAT: $it").setFontSize(10f)) }
    document.add(Paragraph(""))
    // Customer Information
    document.add(Paragraph("Invoice").setBold().setFontSize(14f))
    document.add(Paragraph("To: ${customer.name}").setFontSize(12f))
    document.add(Paragraph(customer.email).setFontSize(10f))
    document.add(Paragraph("Issue Date: ${java.util.Date(invoice.issueDate)}").setFontSize(10f))
    document.add(Paragraph(""))
    // Tasks Table
    val table = Table(UnitValue.createPercentArray(floatArrayOf(30f, 20f, 15f, 20f, 15f))).useAllAvailableWidth()
    table.addHeaderCell(Cell().add(Paragraph("Description").setBold()))
    table.addHeaderCell(Cell().add(Paragraph("Labor Cost").setBold()))
    table.addHeaderCell(Cell().add(Paragraph("Labor Type").setBold()))
    table.addHeaderCell(Cell().add(Paragraph("Material Cost").setBold()))
    table.addHeaderCell(Cell().add(Paragraph("Material Type").setBold()))
    tasks.forEach { task ->
        table.addCell(Cell().add(Paragraph(task.description)))
        table.addCell(Cell().add(Paragraph(task.laborCost.toString())))
        table.addCell(Cell().add(Paragraph(task.laborType)))
        table.addCell(Cell().add(Paragraph(task.materialCost.toString())))
        table.addCell(Cell().add(Paragraph(task.materialType)))
    }
    document.add(table)
    document.add(Paragraph(""))
    document.add(Paragraph("Total: ${invoice.totalAmount}").setBold().setFontSize(12f).setTextAlignment(TextAlignment.RIGHT))
    document.close()
}
fun generateQuote(quote: Quote, businessProfile: BusinessProfile, customer: Customer, tasks: List<Task>, outputPath: String) {
    val pdfWriter = PdfWriter(outputPath)
    val pdfDocument = PdfDocument(pdfWriter)
    val document = Document(pdfDocument)
    businessProfile.logoPath?.let { document.add(Image(ImageDataFactory.create(it))) }
    document.add(Paragraph(businessProfile.companyName))
    document.add(Paragraph(businessProfile.companyAddress))
    document.add(Paragraph("Phone: ${businessProfile.companyPhone}"))
    document.add(Paragraph("Email: ${businessProfile.companyEmail}"))
    businessProfile.vatNumber?.let { document.add(Paragraph("VAT: $it")) }
    document.add(Paragraph("To: ${customer.name}"))
    document.add(Paragraph(customer.email))
    document.add(Paragraph("Quote Date: ${java.util.Date(quote.issueDate)}"))
    document.add(Paragraph("Total: ${quote.totalAmount}"))
    tasks.forEach { task ->
        document.add(Paragraph("${task.description}: Labor ${task.laborCost} (${task.laborType}), Material ${task.materialCost} (${task.materialType})"))
    }
    document.close()
}
