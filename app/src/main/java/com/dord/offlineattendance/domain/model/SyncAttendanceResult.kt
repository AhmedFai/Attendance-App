package com.dord.offlineattendance.domain.model

sealed class SyncAttendanceResult {
    object Loading : SyncAttendanceResult()
    object NoPendingData : SyncAttendanceResult()
    object Success : SyncAttendanceResult()
    object NoInternet : SyncAttendanceResult()
    data class Error(val message: String) : SyncAttendanceResult()
}