package com.dord.offlineattendance.presentation.home

import com.dord.offlineattendance.util.UiText


sealed class HomeUIEvent {
    data class ShowToast(val message: UiText) : HomeUIEvent()
}
