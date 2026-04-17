package com.dord.offlineattendance.domain.usecase.domain

import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.domain.repository.DomainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelectedDomainUseCase @Inject constructor(
    private val repo: DomainRepository
) {
    operator fun invoke(): Flow<DomainType> = repo.selectedDomain
}