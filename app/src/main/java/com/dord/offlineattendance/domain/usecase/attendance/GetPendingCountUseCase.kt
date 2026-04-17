package com.dord.offlineattendance.domain.usecase.attendance

import com.dord.offlineattendance.domain.repository.AttendanceRepository
import javax.inject.Inject

class GetPendingCountUseCase @Inject constructor(
    private val repo: AttendanceRepository
) {
    suspend operator fun invoke(): Int {
        return repo.getPendingCount()
    }
}