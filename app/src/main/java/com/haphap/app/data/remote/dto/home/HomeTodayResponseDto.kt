package com.haphap.app.data.remote.dto.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeTodayResponseDto(
    @SerialName("cumulatedCount")
    val cumulatedCount: Int,
    @SerialName("onGoingCount")
    val onGoingCount: Int,
    @SerialName("announcedCount")
    val announcedCount: Int,
)