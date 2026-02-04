package com.example.attendance.domain.usecase.attendance

import com.example.attendance.domain.repository.AttendanceRepository
import javax.inject.Inject

class GetPendingCountUseCase @Inject constructor(
    private val repo: AttendanceRepository
) {
    suspend operator fun invoke(): Int {
        return repo.getPendingCount()
    }
}