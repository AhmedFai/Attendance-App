package com.example.attendance.domain.repository

import com.example.attendance.data.local.entity.CandidateEntity
import kotlinx.coroutines.flow.Flow

interface CandidateRepository {

    suspend fun insertCandidates(candidates: List<CandidateEntity>)

    fun getCandidatesByBatch(batchId: Long): Flow<List<CandidateEntity>>

    suspend fun getCandidateById(candidateId: String): CandidateEntity?

    suspend fun clearAllCandidates()

}