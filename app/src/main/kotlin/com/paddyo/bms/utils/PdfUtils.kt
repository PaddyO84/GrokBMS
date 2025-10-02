package com.paddyo.bms.utils
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.io.image.ImageDataFactory
import com.paddyo.bms.data.entities.*
fun generateInvoice(
    invoice: Invoice,
    businessProfile: BusinessProfile,
    customer: Customer,
    tasks: List<Task>,
    outputPath: String
) {
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
    document.add(Paragraph("Invoice Date: ${java.util.Date(invoice.issueDate)}"))
    document.add(Paragraph("Total: ${invoice.totalAmount}"))
    tasks.forEach { task ->
        document.add(Paragraph("${task.description}: Labor ${task.laborCost} (${task.laborType}), Material ${task.materialCost} (${task.materialType})"))
    }
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