package com.dord.offlineattendance.domain.usecase.attendance

import com.dord.offlineattendance.domain.repository.AttendanceRepository
import javax.inject.Inject

class GetCheckOutTimeUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    suspend operator fun invoke(
        userId: String,
        userType: String,
        batchId: Long,
        date: String
    ): String? {
        return repository.getCheckOutTime(userId, userType, batchId, date)
    }
}