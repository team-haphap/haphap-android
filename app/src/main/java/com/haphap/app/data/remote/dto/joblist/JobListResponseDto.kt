package com.haphap.app.data.remote.dto.joblist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobListResponseDto(
    @SerialName("postings")
    val postings: List<JobItemDto>,
)

@Serializable
data class JobItemDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("category")
    val category: String,
    @SerialName("nextStage")
    val nextStage: String?,
    @SerialName("daysUntilNextStage")
    val daysUntilNextStage: Int?,
    @SerialName("imageUrl")
    val imageUrl: String,
)
