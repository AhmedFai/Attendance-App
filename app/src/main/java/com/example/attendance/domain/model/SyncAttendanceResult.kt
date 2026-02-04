package com.example.attendance.domain.model

import android.content.SyncResult

sealed class SyncAttendanceResult {
    object Loading : SyncAttendanceResult()
    object NoPendingData : SyncAttendanceResult()
    object Success : SyncAttendanceResult()
    object NoInternet : SyncAttendanceResult()
    data class Error(val message: String) : SyncAttendanceResult()
}