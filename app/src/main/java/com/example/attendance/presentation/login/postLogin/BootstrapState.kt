package com.example.attendance.presentation.login.postLogin

import com.example.attendance.util.UiText

sealed class BootstrapState {
    object Idle : BootstrapState()
    object Loading : BootstrapState()
    object Success : BootstrapState()
    data class Error(val message: UiText) : BootstrapState()
}