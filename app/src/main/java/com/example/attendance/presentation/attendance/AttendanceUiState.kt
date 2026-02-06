package com.example.attendance.presentation.attendance

import com.example.attendance.data.local.entity.BatchEntity
import com.example.attendance.data.local.entity.CandidateEntity
import com.example.attendance.data.local.entity.FacultyEntity

data class AttendanceUiState(
    val isLoading: Boolean = true,
    val candidate: CandidateEntity? = null,
    val faculty: FacultyEntity? = null,
    val batch: BatchEntity? = null,

    val isCheckedInToday: Boolean = false,
    val isCheckedOutToday: Boolean = false,

    val checkInTime: String? = null,
    val checkOutTime: String? = null,
    val totalHours: String = "00:00 hrs",


    val canCheckIn: Boolean = true,
    val canCheckOut: Boolean = false,

    val error: String? = null,
    val hasLoadedOnce: Boolean = false,

    val showSuccessSheet: Boolean = false,
    val successType: String? = null
)
