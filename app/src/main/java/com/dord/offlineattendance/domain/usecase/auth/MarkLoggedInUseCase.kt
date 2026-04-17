package com.dord.offlineattendance.domain.usecase.auth

import com.dord.offlineattendance.domain.repository.AuthRepository
import javax.inject.Inject

class MarkLoggedInUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() {
        repo.markLoggedIn()
    }
}