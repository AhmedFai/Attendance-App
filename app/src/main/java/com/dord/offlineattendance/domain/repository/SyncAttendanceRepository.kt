package com.dord.offlineattendance.domain.repository

import com.dord.offlineattendance.domain.model.SyncAttendanceResult

interface SyncAttendanceRepository {
    suspend fun syncPendingAttendances(): SyncAttendanceResult
}