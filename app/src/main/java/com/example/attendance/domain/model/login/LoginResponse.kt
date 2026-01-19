package com.example.attendance.domain.model.login

data class LoginResponse(
    val accessToken: String,
    val responseCode: Int,
    val responseDesc: String
)