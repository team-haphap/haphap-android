package com.haphap.app.data.model

data class KakaoLoginModel(
    val accessToken: String,
    val refreshToken: String,
    val name: String,
    val anonymousName: String,
)