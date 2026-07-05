package com.haphap.app.data.repository.impl.auth

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import com.haphap.app.data.mapper.auth.toModel
import com.haphap.app.data.model.auth.KakaoLoginModel
import com.haphap.app.data.remote.datasource.api.auth.AuthDataSource
import com.haphap.app.data.repository.api.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
): AuthRepository {
    override suspend fun postKakaoLogin(accessToken: String): Result<KakaoLoginModel> =
        suspendRunCatching {
            val response = authDataSource.postKakaoLogin(accessToken)

            localTokenDataSource.setAccessToken(response.accessToken)
            localTokenDataSource.setRefreshToken(response.refreshToken)

            response.toModel()
         }
}