package com.dord.offlineattendance.domain.usecase.attendance

import com.dord.offlineattendance.domain.model.SyncAttendanceResult
import com.dord.offlineattendance.domain.repository.SyncAttendanceRepository
import javax.inject.Inject

class SyncAttendanceUseCase @Inject constructor(
    private val repository: SyncAttendanceRepository
) {

    suspend operator fun invoke(): SyncAttendanceResult {
        return repository.syncPendingAttendances()
    }

}