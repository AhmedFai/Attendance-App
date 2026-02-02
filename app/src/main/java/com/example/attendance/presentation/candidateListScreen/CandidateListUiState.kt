package com.example.attendance.presentation.candidateListScreen

import com.example.attendance.data.local.entity.CandidateEntity

data class CandidateListUiState(
    val isLoading: Boolean = false,
    val candidates: List<CandidateEntity> = emptyList(),
    val hasLoadedOnce: Boolean = false
)