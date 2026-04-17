package com.dord.offlineattendance.data.repository

import com.dord.offlineattendance.data.local.dao.BatchDao
import com.dord.offlineattendance.data.local.entity.BatchEntity
import com.dord.offlineattendance.domain.repository.BatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BatchRepositoryImpl @Inject constructor(
    private val batchDao: BatchDao
) : BatchRepository {
    override suspend fun insertBatches(batches: List<BatchEntity>) {
        return batchDao.insertBatches(batches)
    }

    override fun getActiveBatches(): Flow<List<BatchEntity>> {
        return batchDao.getActiveBatches()
    }

    override suspend fun getBatchById(batchId: Long): BatchEntity? {
        return batchDao.getBatchById(batchId)
    }

    override suspend fun clearBatch() {
        return batchDao.clearBatch()
    }
}