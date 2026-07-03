package com.haphap.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val name: String,
    val anonymousName: String,
)