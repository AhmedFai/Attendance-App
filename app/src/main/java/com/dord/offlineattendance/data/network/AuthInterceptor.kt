package com.dord.offlineattendance.data.network

import com.dord.offlineattendance.BuildConfig
import com.dord.offlineattendance.data.datastore.AppPreferences
import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.util.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val preferences: AppPreferences
) : Interceptor {
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
            .addHeader(Constants.APP_VERSION, BuildConfig.VERSION_NAME)
            .build()

        return chain.proceed(newRequest)
    }
}