package com.haphap.app.data.remote.dto.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobDetailStageStatisticDto(
    @SerialName("stageId") val stageId: Int,
    @SerialName("passCount") val passCount: Int,
    @SerialName("failCount") val failCount: Int,
    @SerialName("pendingCount") val pendingCount: Int,
)
