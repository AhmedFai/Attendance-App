package com.example.attendance.domain.usecase.attendance

import com.example.attendance.data.local.entity.AttendanceEntity
import com.example.attendance.domain.repository.AttendanceRepository
import javax.inject.Inject

class MarkCheckInUseCase @Inject constructor(
    private val repository: AttendanceRepository
) {
    suspend operator fun invoke(attendanceEntity: AttendanceEntity){
        return repository.markCheckIn(attendanceEntity)
    }
}