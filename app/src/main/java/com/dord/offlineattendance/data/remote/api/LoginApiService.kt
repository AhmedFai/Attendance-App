package com.dord.offlineattendance.data.remote.api

import com.dord.offlineattendance.domain.model.login.LoginRequest
import com.dord.offlineattendance.domain.model.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface LoginApiService {
    @POST
    suspend fun login(
        @Url fullUrl: String,
        @Body request: LoginRequest
    ): Response<LoginResponse>
}