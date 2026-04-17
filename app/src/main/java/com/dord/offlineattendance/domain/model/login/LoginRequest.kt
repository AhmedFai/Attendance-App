package com.dord.offlineattendance.domain.model.login

data class LoginRequest(
    val loginId: String,
    val password: String
)