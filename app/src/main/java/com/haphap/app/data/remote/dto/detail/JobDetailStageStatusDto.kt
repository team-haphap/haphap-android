package com.haphap.app.data.remote.dto.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobDetailStageStatusListDto(
    @SerialName("stages") val stages: List<JobDetailStageStatusDto>,
    @SerialName("defaultSelectedStageId") val defaultSelectedStageId: Int,
)

@Serializable
data class JobDetailStageStatusDto(
    @SerialName("stageId") val stageId: Int,
    @SerialName("stageName") val stageName: String,
    @SerialName("status") val status: String,
)
