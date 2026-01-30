package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.CandidateDao
import com.example.attendance.data.local.entity.CandidateEntity
import com.example.attendance.domain.repository.CandidateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CandidateRepositoryImpl @Inject constructor(
    private val candidateDao: CandidateDao
) : CandidateRepository {
    override suspend fun insertCandidates(candidates: List<CandidateEntity>) {
        candidateDao.insertCandidate(candidates)
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