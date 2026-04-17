package com.dord.offlineattendance.domain.usecase.batch

import com.dord.offlineattendance.domain.repository.BatchRepository
import javax.inject.Inject

class ClearBatchUseCase @Inject constructor(
    private val repository: BatchRepository
) {
    suspend operator fun invoke() {
        repository.clearBatch()
    }
}