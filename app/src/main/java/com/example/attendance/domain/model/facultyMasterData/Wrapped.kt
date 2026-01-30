package com.example.attendance.domain.model.facultyMasterData

data class Wrapped(
    val aadhaarNo: String,
    val attendanceDate: String,
    val attendanceFlag: String,
    val batchId: Int,
    val batchRegNo: String,
    val checkIn: String,
    val checkOut: String,
    val dob: String,
    val emailId: String,
    val endDate: String,
    val facultyName: String,
    val gender: String,
    val latitute: String,
    val loginId: String,
    val longitute: String,
    val mobileNo: String,
    val radius: Int,
    val startDate: String,
    val totalHours: String
)