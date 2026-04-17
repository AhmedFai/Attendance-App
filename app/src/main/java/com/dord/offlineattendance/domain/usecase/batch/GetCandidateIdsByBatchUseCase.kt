package com.dord.offlineattendance.domain.usecase.batch

import com.dord.offlineattendance.domain.repository.CandidateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCandidateIdsByBatchUseCase @Inject constructor(
    private val repository: CandidateRepository
) {

    operator fun invoke(batchId: Long): Flow<List<String>> {
        return repository.getCandidatesByBatch(batchId)
            .map { list ->
                list.map { it.candidateId }
            }
    }
}