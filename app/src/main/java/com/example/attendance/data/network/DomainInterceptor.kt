package com.example.attendance.data.network

import android.util.Log
import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.domain.model.DomainType
import com.example.attendance.util.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DomainInterceptor @Inject constructor(
    private val domainDataStore: AppPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val domain = runBlocking {
            domainDataStore.getSelectedDomain()
        }

        val baseUrl = when(domain){
            DomainType.RSETI -> Constants.RSETI
            DomainType.DDUGKY -> Constants.DDUGKY
        }

        val httpUrl = baseUrl.toHttpUrl()

        val newUrl = originalRequest.url.newBuilder()
            .scheme(httpUrl.scheme)
            .host(httpUrl.host)
            .port(httpUrl.port)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        Log.e("DOMAIN_INTERCEPTOR",
            "Selected=$domain | FinalURL=${newUrl}"
        )

        return chain.proceed(newRequest)

    }
}