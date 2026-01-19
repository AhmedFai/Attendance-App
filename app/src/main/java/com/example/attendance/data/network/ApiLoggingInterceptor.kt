package com.example.attendance.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import javax.inject.Inject

class ApiLoggingInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val buffer = Buffer()
        request.body?.writeTo(buffer)

        val requestBody = try {
            buffer.readString(Charset.forName("UTF-8"))
        }catch (e: Exception){
            "N/A"
        }

        Log.e("API_REQUEST", """
            ➡️ URL: ${request.url}
            ➡️ METHOD: ${request.method}
            ➡️ HEADERS: ${request.headers}
            ➡️ BODY: $requestBody
        """.trimIndent())

        val response = chain.proceed(request)

        val source = response.body?.source()
        source?.request(Long.MAX_VALUE)

        val responseBody = try {
            source?.buffer?.clone()?.readString(Charset.forName("UTF-8"))
        } catch (e: Exception) {
            "N/A"
        }

        Log.e("API_RESPONSE", """
            ⬅️ URL: ${response.request.url}
            ⬅️ CODE: ${response.code}
            ⬅️ BODY: $responseBody
        """.trimIndent())

        return response
    }
}