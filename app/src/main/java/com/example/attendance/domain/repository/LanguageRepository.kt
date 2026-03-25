package com.example.attendance.domain.repository

import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(code: String)
}