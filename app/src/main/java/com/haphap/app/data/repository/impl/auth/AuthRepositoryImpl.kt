package com.haphap.app.data.repository.impl.auth

import com.haphap.app.core.fcm.FirebaseMessagingManager
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalFcmDataSource
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import com.haphap.app.data.mapper.auth.toModel
import com.haphap.app.data.model.auth.KakaoLoginModel
import com.haphap.app.data.remote.datasource.api.auth.AuthDataSource
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.remote.dto.auth.LogoutRequestDto
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.auth.AuthRepository
import jakarta.inject.Inject
import retrofit2.HttpException

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
    private val localFcmDataSource: LocalFcmDataSource,
    private val firebaseMessagingManager: FirebaseMessagingManager
): AuthRepository {
    override suspend fun postKakaoLogin(accessToken: String): Result<KakaoLoginModel> =
        suspendRunCatching {
            val response = authDataSource.postKakaoLogin(KakaoLoginRequestDto(accessToken)).checkData()


            localTokenDataSource.setAccessToken(response.accessToken)
            localTokenDataSource.setRefreshToken(response.refreshToken)

            response.toModel()
        }

    override suspend fun postLogout(): Result<Unit> =
        suspendRunCatching {
            try {
                val deviceId = firebaseMessagingManager.getInstallationId()
                    ?: throw IllegalStateException("Device id is null")
                val response = authDataSource.postLogout(LogoutRequestDto(deviceId))

                if (!response.isSuccessful) throw HttpException(response)
            } finally {
                localTokenDataSource.clearTokens()
                localFcmDataSource.clearFcmToken()
            }
        }
}
