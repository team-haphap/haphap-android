package com.haphap.app.data.remote.dto.mypage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountInfoResponseDto(
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("provider")
    val provider: String,
)
