package com.example.attendance.domain.model.candidateMasterData

data class Wrapped(
    val aadhaarNo: String,
    val address: String,
    val attendanceDate: String,
    val attendanceFlag: String,
    val batchId: Int,
    val batchName: String,
    val batchRegNo: String,
    val candidateId: String,
    val candidateName: String,
    val checkIn: String,
    val checkOut: String,
    val dateOfBirth: String,
    val emailId: String,
    val endDate: String,
    val gender: String,
    val latitude: String,
    val longitude: String,
    val mobileNo: String,
    val radius: Int,
    val rollNo: Int,
    val startDate: String,
    val totalHours: String
)