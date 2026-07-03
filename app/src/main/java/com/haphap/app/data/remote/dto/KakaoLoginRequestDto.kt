package com.haphap.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginRequestDto(
    val accessToken: String,
)