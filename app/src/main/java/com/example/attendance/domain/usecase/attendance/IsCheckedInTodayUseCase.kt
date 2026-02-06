package com.example.attendance.domain.usecase.attendance

import com.example.attendance.domain.repository.AttendanceRepository
import javax.inject.Inject

class IsCheckedInTodayUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    suspend operator fun invoke(userId: String, userType: String, batchId: Long, date: String): Boolean {
        return repository.isCheckedInToday(userId, userType, batchId, date)
    }
}