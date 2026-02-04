package com.example.attendance.domain.repository

import com.example.attendance.domain.model.UserSession
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val userSession: Flow<UserSession?>
    suspend fun saveSession(userId: String, token: String)
    suspend fun markLoggedIn()
    suspend fun clearSession()
}