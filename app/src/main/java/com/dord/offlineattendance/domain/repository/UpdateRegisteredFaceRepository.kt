package com.dord.offlineattendance.domain.repository

import com.dord.offlineattendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceRequest
import com.dord.offlineattendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceResponse
import com.dord.offlineattendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface UpdateRegisteredFaceRepository {
    suspend fun updateRegisteredFace(
        updateRegisteredFaceRequest: UpdateRegisteredFaceRequest
    ): Flow<ApiState<UpdateRegisteredFaceResponse>>
}