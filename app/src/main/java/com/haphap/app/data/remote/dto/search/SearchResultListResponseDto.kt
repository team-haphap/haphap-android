package com.haphap.app.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultListResponseDto(
    @SerialName("postings")
    val postings: List<SearchResultItemDto>,
    @SerialName("page")
    val page: Int,
    @SerialName("size")
    val size: Int,
    @SerialName("hasNext")
    val hasNext: Boolean,
)

@Serializable
data class SearchResultItemDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("title")
    val title: String,
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("nextStage")
    val nextStage: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("dDay")
    val dDay: String,
)
