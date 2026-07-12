package com.haphap.app.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AutoCompleteListDto(
    @SerialName("results")
    val results: List<AutoCompleteItemDto>,
)

@Serializable
data class AutoCompleteItemDto(
    @SerialName("type")
    val type: String,
    @SerialName("name")
    val name: String,
    @SerialName("highlightRanges")
    val highlightRanges: List<HighlightRangeDto>,
    @SerialName("postingId")
    val postingId: Int? = null,
)

@Serializable
data class HighlightRangeDto(
    @SerialName("start")
    val start: Int,
    @SerialName("end")
    val end: Int,
)
