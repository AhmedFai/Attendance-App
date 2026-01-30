package com.example.attendance.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "faculty",
    primaryKeys = ["loginId", "batchId"]
)
data class FacultyEntity(
    val loginId: String,
    val facultyName: String,
    val emailId: String,
    val mobileNo: String,
    val gender: String,
    val dob: String,
    val batchId: Long,
    val batchRegNo: String,
    val startDate: String,
    val endDate: String
)
