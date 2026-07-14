package com.haphap.app.data.remote.dto.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationCheckRequestDto(
    @SerialName("result")
    val result: String,
)