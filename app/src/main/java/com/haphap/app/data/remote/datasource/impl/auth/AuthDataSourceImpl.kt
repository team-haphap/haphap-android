package com.haphap.app.data.remote.datasource.impl.auth

import com.haphap.app.data.remote.datasource.api.auth.AuthDataSource
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto
import com.haphap.app.data.remote.service.auth.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {

    override suspend fun postKakaoLogin(accessToken: String): KakaoLoginResponseDto {
        val response = authService.kakaoLogin(KakaoLoginRequestDto(accessToken = accessToken))
        return response.data ?: throw IllegalStateException("response data is null")
    }
}