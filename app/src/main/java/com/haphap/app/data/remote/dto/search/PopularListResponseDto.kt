package com.haphap.app.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularListResponseDto(
    @SerialName("postings")
    val postings: List<PopularItemDto>,
)

@Serializable
data class PopularItemDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("category")
    val category: String,
    @SerialName("nextStage")
    val nextStage: String,
    @SerialName("daysUntilNextStage")
    val daysUntilNextStage: String,
    @SerialName("imageUrl")
    val imageUrl: String,
)
