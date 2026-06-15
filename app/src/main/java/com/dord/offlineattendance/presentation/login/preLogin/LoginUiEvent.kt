package com.dord.offlineattendance.presentation.login.preLogin

import com.dord.offlineattendance.util.UiText

sealed class LoginUiEvent {
    data class ShowToast(val message: UiText) : LoginUiEvent()
    object StartBootStrap : LoginUiEvent()
    object StartBootStrapDemo : LoginUiEvent()
    object StartFaceSdk : LoginUiEvent()

}