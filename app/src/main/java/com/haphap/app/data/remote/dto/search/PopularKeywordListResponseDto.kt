package com.haphap.app.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularKeywordListResponseDto(
    @SerialName("keywords")
    val keywords: List<PopularKeywordDto>,
)

@Serializable
data class PopularKeywordDto(
    @SerialName("rank")
    val rank: Int,
    @SerialName("keyword")
    val keyword: String,
)
