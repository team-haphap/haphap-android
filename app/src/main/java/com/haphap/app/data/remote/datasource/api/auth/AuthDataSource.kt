package com.haphap.app.data.remote.datasource.api.auth

import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto

interface AuthDataSource {
    suspend fun postKakaoLogin(accessToken: String): KakaoLoginResponseDto
}