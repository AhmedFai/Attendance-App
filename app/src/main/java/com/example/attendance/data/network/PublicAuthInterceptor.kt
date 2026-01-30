package com.example.attendance.data.network

import com.example.attendance.BuildConfig
import com.example.attendance.util.Constants
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