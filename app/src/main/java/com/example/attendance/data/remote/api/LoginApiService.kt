package com.example.attendance.data.remote.api

import com.example.attendance.domain.model.login.LoginRequest
import com.example.attendance.domain.model.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface LoginApiService {
    @POST
    suspend fun login(
        @Url fullUrl: String,
        @Body request: LoginRequest
    ): LoginResponse
}