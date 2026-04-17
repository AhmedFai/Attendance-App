package com.dord.offlineattendance.domain.usecase.attendance

import com.dord.offlineattendance.data.local.entity.AttendanceEntity
import com.dord.offlineattendance.domain.repository.AttendanceRepository
import javax.inject.Inject

class MarkCheckInUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    suspend operator fun invoke(attendanceEntity: AttendanceEntity) {
        return repository.markCheckIn(attendanceEntity)
    }
}