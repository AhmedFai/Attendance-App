package com.dord.offlineattendance.domain.repository

import com.dord.offlineattendance.domain.model.DomainType
import kotlinx.coroutines.flow.Flow

interface DomainRepository {
    val selectedDomain: Flow<DomainType>
    suspend fun saveDomain(domain: DomainType)
}