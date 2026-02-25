package com.example.attendance.domain.usecase.auth

import com.example.attendance.domain.repository.LogoutRepository
import javax.inject.Inject

class LogoutAuthUseCase @Inject constructor(
    private val repo: LogoutRepository
) {

    suspend operator fun invoke() = repo.logout()

}