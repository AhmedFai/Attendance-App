package com.dord.offlineattendance.domain.repository

import com.dord.offlineattendance.domain.model.candidateMasterData.CandidateMasterDataResponse
import com.dord.offlineattendance.util.ApiState
import kotlinx.coroutines.flow.Flow

interface CandidateMasterDataRepository {
    fun getUserMasterData(): Flow<ApiState<CandidateMasterDataResponse>>
}