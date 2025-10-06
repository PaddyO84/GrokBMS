package com.paddyo.bms.utils
import android.content.Context
import android.net.Uri
import java.io.File
fun saveImage(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, "image_${System.currentTimeMillis()}.jpg")
    inputStream?.use { input -> file.outputStream().use { output -> input.copyTo(output) } }
    return file.absolutePath
}
