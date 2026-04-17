package com.dord.offlineattendance.data.repository

import com.dord.offlineattendance.data.datastore.AppPreferences
import com.dord.offlineattendance.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow

class LanguageRepositoryImpl(
    private val dataStore: AppPreferences
) : LanguageRepository {

    override fun getLanguage(): Flow<String> {
        return dataStore.getLanguage
    }

    override suspend fun setLanguage(code: String) {
        dataStore.saveLanguage(code)
    }
}