package com.example.attendance.domain.model.facultyAttendanceData

data class FacultyAttendance(
    val imeiNo: String,
    val attendanceDate: String,
    val batchId: String,
    val checkIn: String?,
    val checkOut: String?,
    val totalHours: String?,
    val address: String,
    val login: String,
    val batchRegNo: String
)
