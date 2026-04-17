package com.dord.offlineattendance.domain.usecase.auth

import com.dord.offlineattendance.domain.repository.LogoutRepository
import javax.inject.Inject

class LogoutAuthUseCase @Inject constructor(
    private val repo: LogoutRepository
) {

    suspend operator fun invoke() = repo.logout()

}