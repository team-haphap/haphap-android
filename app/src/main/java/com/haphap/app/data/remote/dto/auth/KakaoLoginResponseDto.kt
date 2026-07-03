package com.haphap.app.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val name: String,
    val anonymousName: String,
)