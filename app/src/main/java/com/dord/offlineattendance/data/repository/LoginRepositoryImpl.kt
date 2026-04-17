package com.dord.offlineattendance.data.repository

import com.dord.offlineattendance.BuildConfig
import com.dord.offlineattendance.data.remote.api.LoginApiService
import com.dord.offlineattendance.data.datastore.AppPreferences
import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.domain.model.login.LoginRequest
import com.dord.offlineattendance.domain.model.login.LoginResponse
import com.dord.offlineattendance.domain.repository.LoginRepository
import com.dord.offlineattendance.util.ApiState
import com.dord.offlineattendance.util.Constants
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.net.ssl.SSLPeerUnverifiedException

class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApiService,
    private val prefs: AppPreferences
) : LoginRepository {
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
                val response = api.login(fullUrl, loginRequest)
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.responseCode == 200) {
                        emit(ApiState.Success(body))
                    } else {
                        emit(ApiState.Error(body?.responseDesc ?: "Unknown error", null))
                    }
                } else {
                    val errorJson = response.errorBody()?.string()

                    val errorObj = try {
                        Gson().fromJson(errorJson, LoginResponse::class.java)
                    } catch (e: Exception) {
                        null
                    }

                    emit(
                        ApiState.Error(
                            errorObj?.responseDesc ?: response.message(),
                            null
                        )
                    )
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
                when (e) {
                    is SSLPeerUnverifiedException -> {
                        emit(
                            ApiState.Error(
                                "Security issue detected. Please use a secure network.",
                                null
                            )
                        )
                    }
                    else -> {
                        emit(
                            ApiState.Exception(e, null)
                        )
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}