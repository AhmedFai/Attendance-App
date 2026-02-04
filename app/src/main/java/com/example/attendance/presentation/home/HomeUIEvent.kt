package com.example.attendance.presentation.home

import com.example.attendance.util.UiText


sealed class HomeUIEvent{
    data class ShowToast(val message: UiText) : HomeUIEvent()
}
