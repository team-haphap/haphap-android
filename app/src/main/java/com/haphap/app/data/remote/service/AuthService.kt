package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto
import com.haphap.app.data.remote.dto.auth.ReissueResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/auth/kakao")
    suspend fun kakaoLogin(
        @Body request: KakaoLoginRequestDto,
    ): BaseResponse<KakaoLoginResponseDto>

    @POST("api/v1/auth/reissue")
    suspend fun postReissue(
        @Header("Authorization") refreshToken: String,
    ): BaseResponse<ReissueResponseDto>
}
