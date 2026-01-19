package com.example.attendance.domain.usecase.domain

import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.repository.DomainRepository
import javax.inject.Inject

class SaveDomainUseCase @Inject constructor(
    private val repo: DomainRepository
) {
    suspend operator fun invoke(domain: DomainType) = repo.saveDomain(domain)
}