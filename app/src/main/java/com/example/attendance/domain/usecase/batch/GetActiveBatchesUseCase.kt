package com.example.attendance.domain.usecase.batch

import com.example.attendance.data.local.entity.BatchEntity
import com.example.attendance.domain.repository.BatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveBatchesUseCase @Inject constructor(
    private val repository: BatchRepository
) {

     operator fun invoke(): Flow<List<BatchEntity>>{
        return repository.getActiveBatches()
    }

}