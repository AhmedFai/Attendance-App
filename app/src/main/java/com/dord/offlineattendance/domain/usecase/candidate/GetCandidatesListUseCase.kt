package com.dord.offlineattendance.domain.usecase.candidate

import com.dord.offlineattendance.domain.repository.CandidateRepository
import javax.inject.Inject

class GetCandidatesListUseCase @Inject constructor(
    private val repository: CandidateRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getAllCandidates().map { it.candidateId }
    }
}