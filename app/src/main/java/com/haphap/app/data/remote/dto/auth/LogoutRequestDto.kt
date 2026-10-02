package com.haphap.app.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequestDto(
    @SerialName("deviceId")
    val deviceId: String,
) {
}