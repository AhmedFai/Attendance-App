package com.dord.offlineattendance.domain.usecase.auth

import com.dord.offlineattendance.domain.repository.AuthRepository
import javax.inject.Inject

class GetLoginSessionUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke() = repo.userSession
}