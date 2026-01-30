package com.example.attendance.presentation.common

import com.example.attendance.util.UiText

sealed class UiEvent {
    data class ShowToast(val message: UiText) : UiEvent()
}