package com.paddyo.bms.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.io.File

class BackupWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val backupPath = inputData.getString("backup_path") ?: return Result.failure()
        val backupFile = File(backupPath)
        return if (backupFile.exists()) {
            Result.success()
        } else {
            Result.failure()
        }
    }
}
