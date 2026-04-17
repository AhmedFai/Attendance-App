package com.dord.offlineattendance.domain.repository

import com.dord.offlineattendance.domain.model.LogoutResponse
import com.dord.offlineattendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface LogoutRepository {

    suspend fun logout(): Flow<ApiState<LogoutResponse>>
}
