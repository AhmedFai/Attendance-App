package com.dord.offlineattendance.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dord.offlineattendance.domain.model.SyncAttendanceResult
import com.dord.offlineattendance.domain.usecase.attendance.SyncAttendanceUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncAttendanceWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val syncAttendanceUseCase: SyncAttendanceUseCase
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return when (syncAttendanceUseCase()) {
            is SyncAttendanceResult.Error -> Result.retry()
            SyncAttendanceResult.Loading -> Result.retry()
            SyncAttendanceResult.NoInternet -> Result.retry()
            SyncAttendanceResult.NoPendingData -> Result.success()
            SyncAttendanceResult.Success -> Result.success()
        }
    }
}