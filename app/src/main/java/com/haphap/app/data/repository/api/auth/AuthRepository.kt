package com.haphap.app.data.repository.api.auth

import com.haphap.app.data.model.auth.KakaoLoginModel

interface AuthRepository {
    suspend fun postKakaoLogin(accessToken: String): Result<KakaoLoginModel>
}