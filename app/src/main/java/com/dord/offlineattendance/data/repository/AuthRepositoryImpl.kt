package com.dord.offlineattendance.data.repository

import com.dord.offlineattendance.data.datastore.AppPreferences
import com.dord.offlineattendance.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val prefs: AppPreferences
) : AuthRepository {
    override val userSession = prefs.sessionFlow

    override suspend fun saveSession(userId: String, token: String) {
        prefs.saveSession(userId, token)
    }

    override suspend fun markLoggedIn() {
        prefs.markLoggedIn()
    }

    override suspend fun clearSession() {
        prefs.clearSession()
    }
}