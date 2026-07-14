package com.haphap.app.data.remote.dto.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobDetailStageListDto(
    @SerialName("postingId") val postingId: Int,
    @SerialName("stages") val stages: List<JobDetailStageDto>,
)

@Serializable
data class JobDetailStageDto(
    @SerialName("stageId") val stageId: Int,
    @SerialName("stageName") val stageName: String,
    @SerialName("orderIndex") val orderIndex: Int,
)
