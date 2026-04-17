package com.dord.offlineattendance.domain.usecase.attendance

import com.dord.offlineattendance.data.local.entity.CandidateEntity
import com.dord.offlineattendance.domain.repository.CandidateRepository
import javax.inject.Inject

class GetCandidateByIdUseCase @Inject constructor(
    private val repository: CandidateRepository
) {
    suspend operator fun invoke(candidateId: String): CandidateEntity? {
        return repository.getCandidateById(candidateId)
    }
}