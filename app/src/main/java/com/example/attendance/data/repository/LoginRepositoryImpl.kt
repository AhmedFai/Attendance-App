package com.example.attendance.data.repository

import com.example.attendance.data.api.ApiServices
import com.example.attendance.data.api.LoginApiService
import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.model.login.LoginRequest
import com.example.attendance.domain.model.login.LoginResponse
import com.example.attendance.domain.repository.LoginRepository
import com.example.attendance.util.ApiState
import com.example.attendance.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApiService,
    private val prefs: AppPreferences
): LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<ApiState<LoginResponse>> {
        return flow {
            emit(ApiState.Loading())
            try {
                val domain = prefs.getSelectedDomain()

                val baseUrl = when (domain) {
                    DomainType.RSETI -> Constants.RSETI
                    DomainType.DDUGKY -> Constants.DDUGKY
                }

                val fullUrl = baseUrl + "login"
                val response = api.login(fullUrl,loginRequest)
                if (response.responseCode == 200){
                    emit(ApiState.Success(response))
                }else{
                    emit(ApiState.Error(response.responseDesc, null))
                }
            }catch (e: Exception){
                e.printStackTrace()
                emit(ApiState.Exception(e, null))
            }
        }.flowOn(Dispatchers.IO)
    }
}