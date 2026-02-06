package com.example.attendance.domain.usecase.attendance

import com.example.attendance.domain.repository.AttendanceRepository
import javax.inject.Inject

class MarkCheckOutUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    suspend operator fun invoke(userId: String, userType: String, batchId: Long, date: String, checkOut: String, totalHours: String){
        return repository.markCheckOut(userId, userType, batchId, date, checkOut, totalHours)
    }
}