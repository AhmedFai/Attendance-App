package com.example.attendance.domain.usecase.auth

import com.example.attendance.domain.model.login.LoginRequest
import com.example.attendance.domain.repository.LoginRepository
import com.example.attendance.util.AppUtil
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