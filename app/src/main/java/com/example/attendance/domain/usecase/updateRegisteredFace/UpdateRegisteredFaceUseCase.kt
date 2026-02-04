package com.example.attendance.domain.usecase.updateRegisteredFace

import com.example.attendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceRequest
import com.example.attendance.domain.repository.UpdateRegisteredFaceRepository
import javax.inject.Inject

class UpdateRegisteredFaceUseCase @Inject constructor(
    private val repository: UpdateRegisteredFaceRepository
) {

    suspend operator fun invoke(updateRegisteredFaceRequest: UpdateRegisteredFaceRequest) =
        repository.updateRegisteredFace(updateRegisteredFaceRequest)

}