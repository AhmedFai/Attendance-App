package com.example.attendance.domain.model.login

data class LoginRequest(
    val appVersion: String,
    val imeiNo: String,
    val loginId: String,
    val password: String
)