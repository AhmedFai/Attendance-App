package com.example.attendance.domain.usecase.attendance

import com.example.attendance.domain.model.SyncAttendanceResult
import com.example.attendance.domain.repository.AttendanceRepository
import com.example.attendance.domain.repository.SyncAttendanceRepository
import javax.inject.Inject

class SyncAttendanceUseCase @Inject constructor(
    private val repository: SyncAttendanceRepository
) {

    suspend operator fun invoke() : SyncAttendanceResult{
        return repository.syncPendingAttendances()
    }

}