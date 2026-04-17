package com.dord.offlineattendance.domain.usecase.auth

import com.dord.offlineattendance.domain.model.login.LoginRequest
import com.dord.offlineattendance.domain.repository.LoginRepository
import com.dord.offlineattendance.util.AppUtil
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(
        loginRequest: LoginRequest
    ) = loginRepository.login(
        loginRequest.copy(
            password = AppUtil.sha512(loginRequest.password)
        )
    )

}