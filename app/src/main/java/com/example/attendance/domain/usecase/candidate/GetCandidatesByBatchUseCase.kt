package com.example.attendance.domain.usecase.candidate

import com.example.attendance.data.local.entity.CandidateEntity
import com.example.attendance.domain.repository.CandidateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCandidatesByBatchUseCase @Inject constructor(
    private val repository: CandidateRepository
) {

    operator fun invoke(batchId: Long): Flow<List<CandidateEntity>>{
        return repository.getCandidatesByBatch(batchId)
    }

}