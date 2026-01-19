package com.example.attendance.domain.usecase.auth

import com.example.attendance.domain.repository.AuthRepository
import javax.inject.Inject

class GetLoginSessionUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke() = repo.userSession
}