package com.example.attendance.data.network

import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.domain.model.DomainType
import com.example.attendance.util.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val preferences: AppPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val (domain, token) = runBlocking {
            preferences.getSelectedDomain() to preferences.getToken()
        }

        val headerKey = when (domain) {
            DomainType.RSETI -> Constants.RSETI_AUTH_HEADER
            DomainType.DDUGKY -> Constants.DDUGKY_AUTH_HEADER
        }

        val newRequest = original.newBuilder()
            .addHeader(
                headerKey,
                "${Constants.AUTH_PREFIX} $token"
            )
            .build()

        return chain.proceed(newRequest)
    }
}