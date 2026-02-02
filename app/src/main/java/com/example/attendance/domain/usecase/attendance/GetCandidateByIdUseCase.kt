package com.example.attendance.domain.usecase.attendance

import com.example.attendance.data.local.entity.CandidateEntity
import com.example.attendance.domain.repository.CandidateRepository
import javax.inject.Inject

class GetCandidateByIdUseCase @Inject constructor(
    private val repository: CandidateRepository
) {
    suspend operator fun invoke(candidateId: String): CandidateEntity? {
        return repository.getCandidateById(candidateId)
    }
}