package com.haphap.app.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReissueResponseDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("name")
    val name: String? = null,
    @SerialName("anonymousName")
    val anonymousName: String? = null,
    @SerialName("profileImageUrl")
    val profileImageUrl: String? = null,
)
