package com.haphap.app.data.remote.datasource.impl.auth

import com.haphap.app.data.remote.datasource.api.auth.AuthDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto
import com.haphap.app.data.remote.service.auth.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {

    override suspend fun postKakaoLogin(requestDto: KakaoLoginRequestDto): BaseResponse<KakaoLoginResponseDto> {
        return authService.kakaoLogin(requestDto)
    }
}