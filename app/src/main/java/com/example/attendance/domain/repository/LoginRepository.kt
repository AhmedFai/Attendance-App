package com.example.attendance.domain.repository

import com.example.attendance.domain.model.login.LoginRequest
import com.example.attendance.domain.model.login.LoginResponse
import com.example.attendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(
        loginRequest: LoginRequest
    ): Flow<ApiState<LoginResponse>>

}