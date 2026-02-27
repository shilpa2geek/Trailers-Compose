package com.geek.cinemax.data.remote.interceptor

import com.geek.cinemax.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.api_key}")
            .build()
        return chain.proceed(request)
    }
}