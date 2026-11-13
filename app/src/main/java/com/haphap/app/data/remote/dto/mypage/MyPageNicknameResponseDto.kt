package com.haphap.app.data.remote.dto.mypage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageNicknameResponseDto(
    @SerialName("anonymousName")
    val anonymousName: String,
)