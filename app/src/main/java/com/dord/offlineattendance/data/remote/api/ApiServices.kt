package com.dord.offlineattendance.data.remote.api

import com.dord.offlineattendance.domain.model.AttendanceResponse
import com.dord.offlineattendance.domain.model.LogoutResponse
import com.dord.offlineattendance.domain.model.candidateAttendanceData.CandidateAttendanceRequest
import com.dord.offlineattendance.domain.model.candidateMasterData.CandidateMasterDataResponse
import com.dord.offlineattendance.domain.model.facultyAttendanceData.FacultyAttendanceRequest
import com.dord.offlineattendance.domain.model.facultyMasterData.FacultyMasterDataResponse
import com.dord.offlineattendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceRequest
import com.dord.offlineattendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST
    suspend fun syncCandidateAttendance(
        @Url fullUrl: String,
        @Body body: CandidateAttendanceRequest
    ): Response<AttendanceResponse>

    @POST
    suspend fun syncFacultyAttendance(
        @Url fullUrl: String,
        @Body body: FacultyAttendanceRequest
    ): Response<AttendanceResponse>

    @POST
    suspend fun updateRegisteredFace(
        @Url fullUrl: String,
        @Body body: UpdateRegisteredFaceRequest
    ): UpdateRegisteredFaceResponse

    @POST
    suspend fun logout(
        @Url fullUrl: String
    ): Response<LogoutResponse>
}