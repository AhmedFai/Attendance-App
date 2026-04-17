package com.dord.offlineattendance.domain.usecase.candidate

import com.dord.offlineattendance.data.local.entity.CandidateEntity
import com.dord.offlineattendance.domain.repository.CandidateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCandidatesByBatchUseCase @Inject constructor(
    private val repository: CandidateRepository
) {

    operator fun invoke(batchId: Long): Flow<List<CandidateEntity>> {
        return repository.getCandidatesByBatch(batchId)
    }

}