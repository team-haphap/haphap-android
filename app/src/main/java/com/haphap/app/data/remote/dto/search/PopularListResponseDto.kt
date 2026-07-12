package com.haphap.app.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularListResponseDto(
    @SerialName("postings")
    val postings: List<PostingDto>,
)

@Serializable
data class PostingDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("category")
    val category: String,
    @SerialName("content")
    val content: String,
    @SerialName("nextStage")
    val nextStage: String,
    @SerialName("daysUntilNextStage")
    val daysUntilNextStage: Int,
    @SerialName("imageUrl")
    val imageUrl: String,
)
