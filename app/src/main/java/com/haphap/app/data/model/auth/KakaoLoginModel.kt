package com.haphap.app.data.model.auth

data class KakaoLoginModel(
    val accessToken: String,
    val refreshToken: String,
    val name: String,
    val anonymousName: String,
)