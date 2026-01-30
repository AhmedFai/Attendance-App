package com.example.attendance.domain.usecase.candidate

import com.example.attendance.data.local.entity.CandidateEntity
import com.example.attendance.domain.repository.CandidateRepository
import javax.inject.Inject

class InsertCandidatesUseCase @Inject constructor(
    private val repository: CandidateRepository
) {
    suspend operator fun invoke(candidates: List<CandidateEntity>) {
        repository.insertCandidates(candidates)
    }
}