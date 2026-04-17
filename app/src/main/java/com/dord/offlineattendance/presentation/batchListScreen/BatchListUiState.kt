package com.dord.offlineattendance.presentation.batchListScreen

import com.dord.offlineattendance.data.local.entity.BatchEntity


data class BatchListUiState(
    val isLoading: Boolean = true,
    val batches: List<BatchEntity> = emptyList()
)