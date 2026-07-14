package com.haphap.app.data.remote.dto.mypage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageResponseDto(
    @SerialName("name")
    val name: String,
    @SerialName("anonymousName")
    val anonymousName: String,
    @SerialName("email")
    val email: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
)
