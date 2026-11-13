package com.haphap.app.data.remote.dto.passcertification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerificationResubmitRequestDto(
    @SerialName("verificationImageIds")
    val verificationImageIds: List<Long>,
)
