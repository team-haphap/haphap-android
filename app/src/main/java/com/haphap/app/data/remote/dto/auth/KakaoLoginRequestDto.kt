package com.haphap.app.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginRequestDto(
    val accessToken: String,
)