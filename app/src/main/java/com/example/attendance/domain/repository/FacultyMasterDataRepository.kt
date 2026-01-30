package com.example.attendance.domain.repository

import com.example.attendance.domain.model.facultyMasterData.FacultyMasterDataResponse
import com.example.attendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface FacultyMasterDataRepository {

    suspend fun getFacultyMasterData() : Flow<ApiState<FacultyMasterDataResponse>>



}