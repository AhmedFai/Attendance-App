package com.dord.offlineattendance.domain.usecase.batch

import com.dord.offlineattendance.data.local.entity.BatchEntity
import com.dord.offlineattendance.domain.repository.BatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveBatchesUseCase @Inject constructor(
    private val repository: BatchRepository
) {

    operator fun invoke(): Flow<List<BatchEntity>> {
        return repository.getActiveBatches()
    }

}