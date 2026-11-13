package com.haphap.app.data.remote.dto.scrap

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScrapResponseDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("isScrapped")
    val isScrapped: Boolean,
)
