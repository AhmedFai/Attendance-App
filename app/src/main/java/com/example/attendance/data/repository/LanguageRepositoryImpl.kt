package com.example.attendance.data.repository

import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.domain.repository.LanguageRepository
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