package com.example.attendance.domain.repository

import com.example.attendance.domain.model.DomainType
import kotlinx.coroutines.flow.Flow

interface DomainRepository {
    val selectedDomain: Flow<DomainType>
    suspend fun saveDomain(domain: DomainType)
}