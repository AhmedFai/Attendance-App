package com.example.attendance.domain.repository

import com.example.attendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceRequest
import com.example.attendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceResponse
import com.example.attendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface UpdateRegisteredFaceRepository {
    suspend fun updateRegisteredFace(
        updateRegisteredFaceRequest: UpdateRegisteredFaceRequest
    ) : Flow<ApiState<UpdateRegisteredFaceResponse>>
}