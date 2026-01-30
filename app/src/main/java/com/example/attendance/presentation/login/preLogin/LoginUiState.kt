package com.example.attendance.presentation.login.preLogin

import com.example.attendance.domain.model.login.LoginResponse
import com.example.attendance.util.UiText

data class LoginUiState(
    val isLoading: Boolean = false,
    val data: LoginResponse? = null,
    val error: UiText? = null,
    val userIdError: UiText? = null,
    val passwordError: UiText? = null
)
