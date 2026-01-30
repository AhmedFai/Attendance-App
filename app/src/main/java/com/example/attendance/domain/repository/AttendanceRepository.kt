package com.example.attendance.domain.repository

import com.example.attendance.data.local.entity.AttendanceEntity

interface AttendanceRepository {

    // Check-in
    suspend fun markCheckIn(attendance: AttendanceEntity)

    // Check-in already?
    suspend fun isCheckedInToday(
        userId: String,
        userType: String,
        date: String
    ): Boolean

    // Check-out already?
    suspend fun isCheckedOutToday(
        userId: String,
        userType: String,
        date: String
    ): Boolean

    // Times
    suspend fun getCheckInTime(
        userId: String,
        userType: String,
        date: String
    ): String?

    suspend fun getCheckOutTime(
        userId: String,
        userType: String,
        date: String
    ): String?

    // Check-out update
    suspend fun markCheckOut(
        userId: String,
        userType: String,
        date: String,
        checkOut: String,
        totalHours: String
    )

    // Sync related
    suspend fun getPendingAttendances(): List<AttendanceEntity>

    suspend fun markAttendancesSynced(attendanceIds: List<Long>)

    // Counts
    suspend fun getPendingCount(): Int
    suspend fun getSyncedCount(): Int

    // Clear
    suspend fun clearAttendance()

}