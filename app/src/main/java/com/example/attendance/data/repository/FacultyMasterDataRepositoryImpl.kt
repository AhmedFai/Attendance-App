package com.example.attendance.data.repository

import com.example.attendance.data.remote.api.ApiServices
import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.model.facultyMasterData.FacultyMasterDataResponse
import com.example.attendance.domain.repository.FacultyMasterDataRepository
import com.example.attendance.util.ApiState
import com.example.attendance.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FacultyMasterDataRepositoryImpl @Inject constructor(
    private val api : ApiServices,
    private val prefs : AppPreferences
) : FacultyMasterDataRepository {
    override suspend fun getFacultyMasterData(): Flow<ApiState<FacultyMasterDataResponse>> {
        return flow {
            emit(ApiState.Loading())
            try {
                val domain = prefs.getSelectedDomain()
                val baseUrl = when(domain){
                    DomainType.RSETI -> Constants.RSETI
                    DomainType.DDUGKY -> Constants.DDUGKY
                }
                val fullUrl = baseUrl + "facultyMasterData"
                val response = api.getFacultyMasterData(fullUrl)
                if (response.responseCode == 200){
                    emit(ApiState.Success(response))
                }else{
                    emit(ApiState.Error(response.responseDesc, null))
                }
            }catch (e : Exception){
                e.printStackTrace()
                emit(ApiState.Exception(e, null))
            }
        }.flowOn(Dispatchers.IO)
    }
}