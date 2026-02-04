package com.example.attendance.presentation.main

sealed class MainUiEvent {
    data class ShowToast(val message: String) : MainUiEvent()
}