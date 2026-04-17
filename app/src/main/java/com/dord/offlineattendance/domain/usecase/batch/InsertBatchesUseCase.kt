package com.dord.offlineattendance.domain.usecase.batch

import com.dord.offlineattendance.data.local.entity.BatchEntity
import com.dord.offlineattendance.domain.repository.BatchRepository
import javax.inject.Inject

class InsertBatchesUseCase @Inject constructor(
    private val repository: BatchRepository
) {

    suspend operator fun invoke(batches: List<BatchEntity>) {
        repository.insertBatches(batches)
    }

}