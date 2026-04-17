package com.dord.offlineattendance.presentation.login.postLogin

import com.dord.offlineattendance.util.UiText

sealed class BootstrapState {
    object Idle : BootstrapState()
    object Loading : BootstrapState()
    object Success : BootstrapState()
    data class Error(val message: UiText) : BootstrapState()
}