package com.dord.offlineattendance.presentation.common

import com.dord.offlineattendance.util.UiText

sealed class UiEvent {
    data class ShowToast(val message: UiText) : UiEvent()
}