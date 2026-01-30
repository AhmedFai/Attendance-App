package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.AttendanceDao
import com.example.attendance.data.local.entity.AttendanceEntity
import com.example.attendance.domain.repository.AttendanceRepository
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceDao: AttendanceDao
) : AttendanceRepository {
    override suspend fun markCheckIn(attendance: AttendanceEntity) {
        attendanceDao.insertAttendance(attendance)
    }

    override suspend fun isCheckedInToday(
        userId: String,
        userType: String,
        date: String
    ): Boolean {
        return attendanceDao.isCheckedInToday(userId, userType, date)
    }

    override suspend fun isCheckedOutToday(
        userId: String,
        userType: String,
        date: String
    ): Boolean {
        return attendanceDao.isCheckedOutToday(userId, userType, date)
    }

    override suspend fun getCheckInTime(
        userId: String,
        userType: String,
        date: String
    ): String? {
        return attendanceDao.getCheckInTime(userId, userType, date)
    }

    override suspend fun getCheckOutTime(
        userId: String,
        userType: String,
        date: String
    ): String? {
        return attendanceDao.getCheckOutTime(userId, userType, date)
    }

    override suspend fun markCheckOut(
        userId: String,
        userType: String,
        date: String,
        checkOut: String,
        totalHours: String
    ) {
        attendanceDao.updateCheckOut(
            userId = userId,
            userType = userType,
            date = date,
            checkOut = checkOut,
            totalHours = totalHours,
            syncStatus = "PENDING"
        )
    }

    override suspend fun getPendingAttendances(): List<AttendanceEntity> {
        return attendanceDao.getPendingAttendances()
    }

    override suspend fun markAttendancesSynced(attendanceIds: List<Long>) {
        attendanceDao.markAttendancesSynced(attendanceIds)
    }

    override suspend fun getPendingCount(): Int {
        return attendanceDao.getPendingCount()
    }

    override suspend fun getSyncedCount(): Int {
        return attendanceDao.getSyncedCount()
    }

    override suspend fun clearAttendance() {
        attendanceDao.clearAttendance()
    }
}