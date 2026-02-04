package com.example.attendance.domain.model.candidateAttendanceData

data class CandidateAttendance(
    val imeiNo: String,
    val attendanceDate: String,
    val batchId: String,
    val candidateId: String,
    val checkIn: String?,
    val checkOut: String?,
    val totalHours: String?,
    val address: String
)
