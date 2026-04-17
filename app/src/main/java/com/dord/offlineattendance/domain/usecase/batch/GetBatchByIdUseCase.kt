package com.dord.offlineattendance.domain.usecase.batch

import com.dord.offlineattendance.domain.repository.BatchRepository
import javax.inject.Inject

class GetBatchByIdUseCase @Inject constructor(
    private val repository: BatchRepository
) {
    suspend operator fun invoke(batchId: Long) = repository.getBatchById(batchId)
}