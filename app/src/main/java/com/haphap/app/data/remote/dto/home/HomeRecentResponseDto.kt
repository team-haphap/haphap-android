package com.haphap.app.data.remote.dto.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeRecentResponseDto(
    @SerialName("views")
    val views: List<HomeRecentDto>,
)

@Serializable
data class HomeRecentDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("title")
    val title: String,
    @SerialName("position")
    val position: String,
    @SerialName("nextStage")
    val nextStage: String?,
    @SerialName("dDayLabel")
    val dDayLabel: String?,
    @SerialName("logoImageUrl")
    val logoImageUrl: String,
)