package com.haphap.app.data.remote.dto.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponseDto(
    @SerialName("registrationId")
    val registrationId: Int,
    @SerialName("verificationStatus")
    val verificationStatus: String? = null,
)
