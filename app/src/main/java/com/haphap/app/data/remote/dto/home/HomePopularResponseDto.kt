package com.haphap.app.data.remote.dto.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomePopularResponseDto(
    @SerialName("views")
    val views: List<HomePopularDto>,
)

@Serializable
data class HomePopularDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("title")
    val title: String,
    @SerialName("category")
    val category: String?,
    @SerialName("nextStage")
    val nextStage: String?,
    @SerialName("dDayLabel")
    val dDayLabel: String?,
    @SerialName("logoImageUrl")
    val logoImageUrl: String,
)