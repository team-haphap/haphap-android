package com.haphap.app.core.network

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.remote.service.AuthService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenDataSource: LocalTokenDataSource,
    private val authService: AuthService,
    private val sessionExpiredNotifier: SessionExpiredNotifier,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.retryCount() >= MAX_RETRY_COUNT) return null

        val failedAccessToken = response.request.header(AUTHORIZATION)?.removePrefix("$BEARER ")

        return synchronized(this) {
            runBlocking {
                val currentAccessToken = tokenDataSource.getAccessToken()

                // 다른 요청이 이미 재발급을 마친 경우 새 토큰으로 재시도
                if (!currentAccessToken.isNullOrBlank() && currentAccessToken != failedAccessToken) {
                    return@runBlocking response.request.withAccessToken(currentAccessToken)
                }

                val refreshToken = tokenDataSource.getRefreshToken()
                if (refreshToken.isNullOrBlank()) {
                    expireSession()
                    return@runBlocking null
                }

                suspendRunCatching { authService.reissue("$BEARER $refreshToken").checkData() }
                    .fold(
                        onSuccess = { tokens ->
                            tokenDataSource.setAccessToken(tokens.accessToken)
                            tokenDataSource.setRefreshToken(tokens.refreshToken)
                            response.request.withAccessToken(tokens.accessToken)
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "token reissue failed")
                            // refreshToken이 무효한 경우에만 세션 만료 처리 (네트워크 오류, 5xx는 토큰 유지)
                            if (throwable is HttpException && throwable.code() == HTTP_UNAUTHORIZED) {
                                expireSession()
                            }
                            null
                        },
                    )
            }
        }
    }

    private suspend fun expireSession() {
        tokenDataSource.clearTokens()
        sessionExpiredNotifier.notifySessionExpired()
    }

    private fun Request.withAccessToken(accessToken: String): Request = newBuilder()
        .header(AUTHORIZATION, "$BEARER $accessToken")
        .build()

    private fun Response.retryCount(): Int {
        var count = 0
        var prior = priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private const val MAX_RETRY_COUNT = 1
        private const val HTTP_UNAUTHORIZED = 401
    }
}
