package com.example.attendance.data.remote.api

import com.example.attendance.domain.model.candidateMasterData.CandidateMasterDataResponse
import com.example.attendance.domain.model.facultyMasterData.FacultyMasterDataResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiServices {

    @GET
    suspend fun getUserMasterData(
        @Url fullUrl: String
    ): CandidateMasterDataResponse

    @GET
    suspend fun getFacultyMasterData(
        @Url fullUrl: String
    ): FacultyMasterDataResponse


}