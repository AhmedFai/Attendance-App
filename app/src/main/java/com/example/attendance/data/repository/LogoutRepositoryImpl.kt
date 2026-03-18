package com.example.attendance.data.repository

import com.example.attendance.BuildConfig
import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.data.remote.api.ApiServices
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.model.LogoutResponse
import com.example.attendance.domain.repository.LogoutRepository
import com.example.attendance.util.ApiState
import com.example.attendance.util.Constants
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.net.ssl.SSLPeerUnverifiedException

class LogoutRepositoryImpl @Inject constructor(
    private val api: ApiServices,
    private val prefs: AppPreferences
) : LogoutRepository{
    override suspend fun logout(): Flow<ApiState<LogoutResponse>> {
        return flow {
            emit(ApiState.Loading())
            try {
                val domain = prefs.getSelectedDomain()

                val baseUrl = when (domain) {
                    DomainType.RSETI -> Constants.RSETI
                    DomainType.DDUGKY -> Constants.DDUGKY
                }

                val fullUrl = baseUrl + "logout"
                val response = api.logout(fullUrl)
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
                        Gson().fromJson(errorJson, LogoutResponse::class.java)
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