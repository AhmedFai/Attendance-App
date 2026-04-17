package com.dord.offlineattendance.presentation.login.preLogin

import com.dord.offlineattendance.domain.model.login.LoginResponse
import com.dord.offlineattendance.util.UiText

data class LoginUiState(
    val isLoading: Boolean = false,
    val data: LoginResponse? = null,
    val error: UiText? = null,
    val userIdError: UiText? = null,
    val passwordError: UiText? = null
)
