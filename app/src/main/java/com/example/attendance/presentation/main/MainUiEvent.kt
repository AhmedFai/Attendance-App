package com.example.attendance.presentation.main

import com.example.attendance.util.UiText

sealed class MainUiEvent {
    data class ShowToast(val message: UiText) : MainUiEvent()
}