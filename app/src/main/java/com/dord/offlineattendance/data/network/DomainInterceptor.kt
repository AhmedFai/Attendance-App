package com.dord.offlineattendance.data.network

import android.util.Log
import com.dord.offlineattendance.data.datastore.AppPreferences
import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.util.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DomainInterceptor @Inject constructor(
    private val domainDataStore: AppPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val domain = runBlocking {
            domainDataStore.getSelectedDomain()
        }

        val baseUrl = when (domain) {
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

        Log.e(
            "DOMAIN_INTERCEPTOR",
            "Selected=$domain | FinalURL=${newUrl}"
        )

        return chain.proceed(newRequest)

    }
}