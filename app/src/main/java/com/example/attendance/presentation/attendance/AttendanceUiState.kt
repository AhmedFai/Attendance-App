package com.example.attendance.presentation.attendance

import com.example.attendance.data.local.entity.CandidateEntity
import com.example.attendance.data.local.entity.FacultyEntity

data class AttendanceUiState(
    val isLoading: Boolean = true,
    val candidate: CandidateEntity? = null,
    val faculty: FacultyEntity? = null,
    val error: String? = null,
    val hasLoadedOnce: Boolean = false
)
