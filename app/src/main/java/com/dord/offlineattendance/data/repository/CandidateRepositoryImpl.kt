package com.dord.offlineattendance.data.repository

import com.dord.offlineattendance.data.local.dao.CandidateDao
import com.dord.offlineattendance.data.local.entity.CandidateEntity
import com.dord.offlineattendance.domain.repository.CandidateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CandidateRepositoryImpl @Inject constructor(
    private val candidateDao: CandidateDao
) : CandidateRepository {
    override suspend fun insertCandidates(candidates: List<CandidateEntity>) {
        candidateDao.insertCandidate(candidates)
    }

    override suspend fun getAllCandidates(): List<CandidateEntity> {
        return candidateDao.getAllCandidate()
    }

    override fun getCandidatesByBatch(batchId: Long): Flow<List<CandidateEntity>> {
        return candidateDao.getCandidateByBatch(batchId)
    }

    override suspend fun getCandidateById(candidateId: String): CandidateEntity? {
        return candidateDao.getCandidateById(candidateId)
    }

    override suspend fun clearAllCandidates() {
        candidateDao.clearCandidates()
    }
}