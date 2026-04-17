package com.dord.offlineattendance.domain.repository

import com.dord.offlineattendance.domain.model.facultyMasterData.FacultyMasterDataResponse
import com.dord.offlineattendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface FacultyMasterDataRepository {
    suspend fun getFacultyMasterData(): Flow<ApiState<FacultyMasterDataResponse>>
}