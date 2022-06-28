package com.example.datalib.utils

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestMiddleware @Inject constructor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
        requestBuilder.addHeader(API_KEY_NAME, API_KEY)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }


    companion object {
        const val API_KEY = "563492ad6f91700001000001937cf8a1abd941529f28b1114c51cf93"
        const val API_KEY_NAME = "Authorization"
    }

}