package com.dord.offlineattendance.domain.usecase.candidate

import com.dord.offlineattendance.data.local.entity.CandidateEntity
import com.dord.offlineattendance.domain.repository.CandidateRepository
import javax.inject.Inject

class InsertCandidatesUseCase @Inject constructor(
    private val repository: CandidateRepository
) {
    suspend operator fun invoke(candidates: List<CandidateEntity>) {
        repository.insertCandidates(candidates)
    }
}