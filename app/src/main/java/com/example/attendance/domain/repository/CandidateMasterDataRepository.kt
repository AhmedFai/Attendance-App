package com.example.attendance.domain.repository

import com.example.attendance.domain.model.candidateMasterData.CandidateMasterDataResponse
import com.example.attendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface CandidateMasterDataRepository {

    fun getUserMasterData() : Flow<ApiState<CandidateMasterDataResponse>>

}