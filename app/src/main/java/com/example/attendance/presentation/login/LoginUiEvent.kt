package com.example.attendance.presentation.login

import com.example.attendance.util.UiText

sealed class LoginUiEvent {
    data class ShowToast(val message: UiText) : LoginUiEvent()
}