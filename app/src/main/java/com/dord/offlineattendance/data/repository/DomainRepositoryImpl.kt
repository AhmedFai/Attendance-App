package com.dord.offlineattendance.data.repository

import com.dord.offlineattendance.data.datastore.AppPreferences
import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.domain.repository.DomainRepository
import kotlinx.coroutines.flow.Flow

class DomainRepositoryImpl(
    private val prefs: AppPreferences
) : DomainRepository {
    override val selectedDomain: Flow<DomainType> =
        prefs.domainFlow

    override suspend fun saveDomain(domain: DomainType) {
        prefs.saveDomain(domain)
    }

}