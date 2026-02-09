package com.example.attendance.data.workManager

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.attendance.data.worker.SyncAttendanceWorker
import java.util.concurrent.TimeUnit

object WorkManagerHelper {

    private const val UNIQUE_ONE_TIME = "attendance_sync_once"
    private const val UNIQUE_PERIODIC = "attendance_sync_periodic"

    fun enqueueOneTime(context: Context) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<SyncAttendanceWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.Companion.getInstance(context)
            .enqueueUniqueWork(
                UNIQUE_ONE_TIME,
                ExistingWorkPolicy.KEEP,
                request
            )
    }


    fun enqueuePeriodic(context: Context) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request =
            PeriodicWorkRequestBuilder<SyncAttendanceWorker>(
                15, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .build()

        WorkManager.Companion.getInstance(context)
            .enqueueUniquePeriodicWork(
                UNIQUE_PERIODIC,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }

}