package com.example.attendance.domain.repository

import com.example.attendance.domain.model.LogoutResponse
import com.example.attendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface LogoutRepository {

    suspend fun logout(): Flow<ApiState<LogoutResponse>>
}
