package com.haphap.app.data.remote.datasource.api.auth

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto
import com.haphap.app.data.remote.dto.auth.LogoutRequestDto
import retrofit2.Response

interface AuthDataSource {
    suspend fun postKakaoLogin(requestDto: KakaoLoginRequestDto): BaseResponse<KakaoLoginResponseDto>
    suspend fun postLogout(requestDto: LogoutRequestDto): Response<Unit>
}