package com.example.attendance.presentation.batchListScreen

import com.example.attendance.data.local.entity.BatchEntity


data class BatchListUiState(
    val isLoading: Boolean = true,
    val batches: List<BatchEntity> = emptyList()
)