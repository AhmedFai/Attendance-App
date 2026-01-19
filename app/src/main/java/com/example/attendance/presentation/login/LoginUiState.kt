package com.example.attendance.presentation.login

import com.example.attendance.domain.model.login.LoginResponse
import com.example.attendance.util.ApiState
import com.example.attendance.util.UiText
import kotlinx.coroutines.flow.Flow

data class LoginUiState(
    val isLoading: Boolean = false,
    val data: LoginResponse? = null,
    val error: UiText? = null,
    val userIdError: UiText? = null,
    val passwordError: UiText? = null
)
