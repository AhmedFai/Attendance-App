package com.example.attendance.domain.model

data class UserSession(
    val userId: String,
    val token: String,
    val isLoggedIn: Boolean
)
