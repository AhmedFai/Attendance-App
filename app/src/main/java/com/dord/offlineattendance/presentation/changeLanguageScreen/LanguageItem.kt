package com.dord.offlineattendance.presentation.changeLanguageScreen

data class LanguageItem(
    val code: String,
    val label: String,
    val short: String,
    val bgColor: androidx.compose.ui.graphics.Color,
    val circleColor: androidx.compose.ui.graphics.Color
)