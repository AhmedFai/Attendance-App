package com.example.attendance.domain.repository

import com.example.attendance.domain.model.SyncAttendanceResult
import com.example.attendance.util.ApiState

interface SyncAttendanceRepository {
    suspend fun syncPendingAttendances() : SyncAttendanceResult
}