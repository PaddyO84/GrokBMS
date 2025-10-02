package com.paddyo.bms.workers
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.paddyo.bms.data.AppDatabase
class ReminderWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val db = AppDatabase.getDatabase(applicationContext)
        val tasks = db.taskDao().getUpcomingTasks(System.currentTimeMillis() + 86_400_000)
        tasks.forEach { task ->
            val notification = NotificationCompat.Builder(applicationContext, "reminders")
                .setContentTitle("Task Reminder")
                .setContentText("Task ${task.description} is due soon")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            NotificationManagerCompat.from(applicationContext).notify(task.id.toInt(), notification)
        }
        return Result.success()
    }
}