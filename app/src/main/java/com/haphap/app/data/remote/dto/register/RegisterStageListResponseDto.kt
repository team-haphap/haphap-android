package com.haphap.app.data.remote.dto.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterStageListResponseDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("stages")
    val stages: List<RegisterStageDto>
)

@Serializable
data class RegisterStageDto(
    @SerialName("stageId")
    val stageId: Int,
    @SerialName("stageName")
    val stageName: String,
    @SerialName("orderIndex")
    val orderIndex: Int,
)