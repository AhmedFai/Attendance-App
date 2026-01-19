package com.example.attendance.data.repository

import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.repository.DomainRepository
import kotlinx.coroutines.flow.Flow

class DomainRepositoryImpl(
    private val prefs: AppPreferences
): DomainRepository {
    override val selectedDomain: Flow<DomainType> =
        prefs.domainFlow

    override suspend fun saveDomain(domain: DomainType) {
        prefs.saveDomain(domain)
    }

}