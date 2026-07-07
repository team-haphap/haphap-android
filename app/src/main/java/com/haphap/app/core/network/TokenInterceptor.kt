package com.haphap.app.core.network

import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor @Inject constructor(
    private val tokenDataSource: LocalTokenDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenDataSource.getAccessToken() }

        val request = chain.request().newBuilder()
            .apply {
                if (!accessToken.isNullOrBlank()) {
                    addHeader(AUTHORIZATION, "$BEARER $accessToken")
                }
            }
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
