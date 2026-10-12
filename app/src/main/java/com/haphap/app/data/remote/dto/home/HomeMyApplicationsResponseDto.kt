package com.haphap.app.data.remote.dto.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeMyApplicationsResponseDto(
    @SerialName("applications")
    val applications: List<HomeMyApplicationDto>,
)

@Serializable
data class HomeMyApplicationDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("title")
    val title: String,
    @SerialName("category")
    val category: String?,
    @SerialName("currentStageStatus")
    val currentStageStatus: String?,
    @SerialName("dDayLabel")
    val dDayLabel: String?,
    @SerialName("logoImageUrl")
    val logoImageUrl: String,
)