package com.example.attendance.domain.repository

import com.example.attendance.data.local.entity.BatchEntity
import kotlinx.coroutines.flow.Flow

interface BatchRepository {

    suspend fun insertBatches(batches: List<BatchEntity>)

    fun getActiveBatches(): Flow<List<BatchEntity>>

    suspend fun getBatchById(batchId: Long): BatchEntity?

    suspend fun clearBatch()
}