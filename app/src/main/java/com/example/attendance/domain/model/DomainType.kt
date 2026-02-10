package com.example.attendance.domain.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.attendance.R

enum class DomainType(
    @StringRes val titleRes: Int,
    val primaryColor: Color,
    val secondaryColor: Color,
    val gradient: List<Color>
) {
    DDUGKY(
        titleRes = R.string.ddugky,
        primaryColor = Color(0xFF403374),
        secondaryColor = Color(0xFF795FDA),
        gradient = listOf(
            Color(0xFF403374),
            Color(0xFF403374),
            Color(0xFF403374).copy(alpha = 0.9f),
            Color(0xFF795FDA)
        )
    ),
    RSETI(
        titleRes = R.string.rseti,
        primaryColor = Color(0xFF00A099),
        secondaryColor = Color(0xFF173430),
        gradient = listOf(
            Color(0xFF00A099),
            Color(0xFF00A099),
            Color(0xFF00A099).copy(alpha = 0.9f),
            Color(0xFF5CCEC6)
        )
    )
}