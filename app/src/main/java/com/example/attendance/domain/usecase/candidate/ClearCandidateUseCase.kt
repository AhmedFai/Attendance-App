package com.example.attendance.domain.usecase.candidate

import com.example.attendance.domain.repository.CandidateRepository
import javax.inject.Inject

class ClearCandidateUseCase @Inject constructor(
    private val repository: CandidateRepository
) {
    suspend operator fun invoke() {
        repository.clearAllCandidates()
    }
}