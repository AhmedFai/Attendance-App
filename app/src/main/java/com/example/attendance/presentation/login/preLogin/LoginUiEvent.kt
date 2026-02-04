package com.example.attendance.presentation.login.preLogin

import com.example.attendance.util.UiText

sealed class LoginUiEvent {
    data class ShowToast(val message: UiText) : LoginUiEvent()
    object StartBootStrap : LoginUiEvent()
    object StartFaceSdk : LoginUiEvent()

}