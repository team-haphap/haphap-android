package com.haphap.app.data.repository.impl.auth

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import com.haphap.app.data.mapper.auth.toModel
import com.haphap.app.data.model.auth.KakaoLoginModel
import com.haphap.app.data.remote.datasource.api.auth.AuthDataSource
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.repository.api.auth.AuthRepository
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
): AuthRepository {
    override suspend fun postKakaoLogin(requestDto: KakaoLoginRequestDto): Result<KakaoLoginModel> =
        suspendRunCatching {
            val response = authDataSource.postKakaoLogin(requestDto)
            val data = response.data ?: throw IllegalStateException("response data is null")

            localTokenDataSource.setAccessToken(data.accessToken)
            localTokenDataSource.setRefreshToken(data.refreshToken)

            data.toModel()
        }
}