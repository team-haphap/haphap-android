package com.haphap.app.data.remote.dto.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobScrapResponseDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("isScrapped")
    val isScrapped: Boolean,
)
