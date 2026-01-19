package com.example.attendance.domain.usecase.auth

import com.example.attendance.domain.model.UserSession
import com.example.attendance.domain.repository.AuthRepository
import javax.inject.Inject

class SaveLoginSessionUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(session: UserSession) =
        repo.saveSession(session)
}