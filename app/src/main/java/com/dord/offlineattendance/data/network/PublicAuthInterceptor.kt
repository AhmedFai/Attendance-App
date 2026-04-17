package com.dord.offlineattendance.data.network

import com.dord.offlineattendance.BuildConfig
import com.dord.offlineattendance.util.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class PublicAuthInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(Constants.APP_VERSION, BuildConfig.VERSION_NAME)
            .build()
        return chain.proceed(request)
    }
}