package com.example.attendance.data.api

import com.example.attendance.domain.model.masterData.ResponseCandidateMasterData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiServices {

    @GET
    suspend fun getUserMasterData(
        @Url fullUrl: String,
        @Body appVersion: String
    ): ResponseCandidateMasterData

}