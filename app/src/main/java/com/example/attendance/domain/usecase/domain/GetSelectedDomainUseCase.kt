package com.example.attendance.domain.usecase.domain

import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.repository.DomainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelectedDomainUseCase @Inject constructor(
    private val repo: DomainRepository
) {
    operator fun invoke(): Flow<DomainType> = repo.selectedDomain
}