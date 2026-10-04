package com.haphap.app.data.remote.datasource.impl.auth

import com.haphap.app.data.remote.datasource.api.auth.AuthDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto
import com.haphap.app.data.remote.dto.auth.LogoutRequestDto
import com.haphap.app.data.remote.service.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {

    override suspend fun postKakaoLogin(requestDto: KakaoLoginRequestDto): BaseResponse<KakaoLoginResponseDto> {
        return authService.kakaoLogin(requestDto)
    }

    override suspend fun postLogout(authorization: String, requestDto: LogoutRequestDto): Response<Unit> {
        return authService.logout(authorization, requestDto)
    }
}
