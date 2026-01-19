package com.example.attendance.data.repository

import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.domain.model.UserSession
import com.example.attendance.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val prefs: AppPreferences
): AuthRepository {
    override val userSession = prefs.sessionFlow

    override suspend fun saveSession(session: UserSession) {
        prefs.saveSession(session)
    }

    override suspend fun clearSession() {
        prefs.clearSession()
    }
}