package com.example.attendance.domain.usecase.batch

import com.example.attendance.domain.repository.BatchRepository
import javax.inject.Inject

class ClearBatchUseCase @Inject constructor(
    private val repository: BatchRepository
) {
    suspend operator fun invoke() {
        repository.clearBatch()
    }
}