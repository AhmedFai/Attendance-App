package com.example.attendance.domain.usecase.batch

import com.example.attendance.data.local.entity.BatchEntity
import com.example.attendance.domain.repository.BatchRepository
import javax.inject.Inject

class InsertBatchesUseCase @Inject constructor(
    private val repository: BatchRepository
) {

    suspend operator fun invoke(batches: List<BatchEntity>){
        repository.insertBatches(batches)
    }

}