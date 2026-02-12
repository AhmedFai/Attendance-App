package com.example.attendance.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: String,
    val userType: String,   // CANDIDATE / FACULTY
    val batchId: Long,
    val batchRegNo: String,

    val attendanceDate: String,

    val checkIn: String?,
    val checkOut: String?,
    val totalHours: String?,

    val address: String?,

    val capturedLatitude: Double?,
    val capturedLongitude: Double?,

    val syncStatus: String // PENDING / SYNCED
)