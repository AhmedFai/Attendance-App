package com.dord.offlineattendance.presentation.main

import com.dord.offlineattendance.util.UiText

sealed class MainUiEvent {
    data class ShowToast(val message: UiText) : MainUiEvent()
}