package com.dord.offlineattendance.domain.usecase.updateRegisteredFace

import com.dord.offlineattendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceRequest
import com.dord.offlineattendance.domain.repository.UpdateRegisteredFaceRepository
import javax.inject.Inject

class UpdateRegisteredFaceUseCase @Inject constructor(
    private val repository: UpdateRegisteredFaceRepository
) {
    suspend operator fun invoke(updateRegisteredFaceRequest: UpdateRegisteredFaceRequest) =
        repository.updateRegisteredFace(updateRegisteredFaceRequest)
}