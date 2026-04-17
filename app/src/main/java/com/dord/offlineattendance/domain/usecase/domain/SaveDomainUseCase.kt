package com.dord.offlineattendance.domain.usecase.domain

import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.domain.repository.DomainRepository
import javax.inject.Inject

class SaveDomainUseCase @Inject constructor(
    private val repo: DomainRepository
) {
    suspend operator fun invoke(domain: DomainType) = repo.saveDomain(domain)
}