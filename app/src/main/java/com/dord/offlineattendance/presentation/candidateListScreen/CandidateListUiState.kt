package com.dord.offlineattendance.presentation.candidateListScreen

import com.dord.offlineattendance.data.local.entity.CandidateEntity

data class CandidateListUiState(
    val isLoading: Boolean = false,
    val candidates: List<CandidateEntity> = emptyList(),
    val hasLoadedOnce: Boolean = false
)