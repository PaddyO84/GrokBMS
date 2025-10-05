package com.paddyo.bms.workers
import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.paddyo.bms.data.entities.AppDatabase
import kotlinx.coroutines.flow.firstOrNull
class BackupWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val db = AppDatabase.getDatabase(applicationContext)
        val backupUriString = db.settingsDao().getSettings().firstOrNull()?.backupLocation ?: return Result.failure()
        val backupUri = Uri.parse(backupUriString)
        val documentFile = DocumentFile.fromTreeUri(applicationContext, backupUri) ?: return Result.failure()
        val databaseFile = applicationContext.getDatabasePath("bms_database")
        val backupDbFile = documentFile.createFile("application/octet-stream", "backup_db_${System.currentTimeMillis()}.db")
        backupDbFile?.let { df ->
            applicationContext.contentResolver.openOutputStream(df.uri)?.use { output ->
                databaseFile.inputStream().use { input -> input.copyTo(output) }
            }
        }
        return Result.success()
    }
}
