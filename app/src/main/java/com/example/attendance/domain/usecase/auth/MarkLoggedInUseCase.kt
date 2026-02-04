package com.example.attendance.domain.usecase.auth

import com.example.attendance.domain.repository.AuthRepository
import javax.inject.Inject

class MarkLoggedInUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() {
        repo.markLoggedIn()
    }
}