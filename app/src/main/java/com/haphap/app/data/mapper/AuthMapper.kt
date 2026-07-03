package com.haphap.app.data.mapper

import com.haphap.app.data.model.KakaoLoginModel
import com.haphap.app.data.remote.dto.KakaoLoginResponseDto

fun KakaoLoginResponseDto.toModel(): KakaoLoginModel = KakaoLoginModel(
    accessToken = accessToken,
    refreshToken = refreshToken,
    name = name,
    anonymousName = anonymousName,
)