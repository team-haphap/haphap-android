package com.haphap.app.data.remote.dto.scrap

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScrapListResponseDto(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("scraps")
    val scraps: List<ScrapItemResponseDto>,
)

@Serializable
data class ScrapItemResponseDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("category")
    val category: String,
    @SerialName("title")
    val title: String,
    @SerialName("logoImageUrl")
    val logoImageUrl: String? = null,
)
