package com.haphap.app.data.repository.api.auth

import com.haphap.app.data.model.auth.KakaoLoginModel
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto

interface AuthRepository {
    suspend fun postKakaoLogin(accessToken: String): Result<KakaoLoginModel>
}