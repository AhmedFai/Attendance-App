package com.dord.offlineattendance.domain.repository

import com.dord.offlineattendance.domain.model.login.LoginRequest
import com.dord.offlineattendance.domain.model.login.LoginResponse
import com.dord.offlineattendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(
        loginRequest: LoginRequest
    ): Flow<ApiState<LoginResponse>>
}