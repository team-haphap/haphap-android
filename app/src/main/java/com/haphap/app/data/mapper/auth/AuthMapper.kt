package com.haphap.app.data.mapper.auth

import com.haphap.app.data.model.auth.KakaoLoginModel
import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto

fun KakaoLoginResponseDto.toModel(): KakaoLoginModel = KakaoLoginModel(
    accessToken = accessToken,
    refreshToken = refreshToken,
    name = name,
    anonymousName = anonymousName,
)