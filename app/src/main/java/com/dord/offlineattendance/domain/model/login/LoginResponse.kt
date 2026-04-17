package com.dord.offlineattendance.domain.model.login

data class LoginResponse(
    val accessToken: String,
    val responseCode: Int,
    val responseDesc: String,
    val userName: String,
    val loginId: String,
    val faceRegistered: String
)