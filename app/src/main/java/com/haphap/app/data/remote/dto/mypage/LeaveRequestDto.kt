package com.haphap.app.data.remote.dto.mypage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaveRequestDto(
    @SerialName("reason")
    val reason: String,
    @SerialName("etcReason")
    val etcReason: String? = null,
) {
}