package com.haphap.app.data.remote.datasource.api.auth

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto

interface AuthDataSource {
    suspend fun postKakaoLogin(requestDto: KakaoLoginRequestDto): BaseResponse<KakaoLoginResponseDto>
}