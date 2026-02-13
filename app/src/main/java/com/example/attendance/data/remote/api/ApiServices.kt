package com.example.attendance.data.remote.api

import com.example.attendance.domain.model.AttendanceResponse
import com.example.attendance.domain.model.candidateAttendanceData.CandidateAttendanceRequest
import com.example.attendance.domain.model.candidateMasterData.CandidateMasterDataResponse
import com.example.attendance.domain.model.facultyAttendanceData.FacultyAttendanceRequest
import com.example.attendance.domain.model.facultyMasterData.FacultyMasterDataResponse
import com.example.attendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceRequest
import com.example.attendance.domain.model.updateRegisteredFace.UpdateRegisteredFaceResponse
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
    ) : UpdateRegisteredFaceResponse
}