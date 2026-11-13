package com.haphap.app.data.remote.dto.passcertification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerificationResubmitResponseDto(
    @SerialName("registrationId")
    val registrationId: Long,
    @SerialName("verificationStatus")
    val verificationStatus: String,
)
