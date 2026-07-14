package com.haphap.app.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchingListResponseDto(
    @SerialName("relatedPostings")
    val relatedPostings: List<AutoCompleteShortcutDto>,
    @SerialName("relatedKeywords")
    val relatedKeywords: List<RelatedKeywordDto>,
)

@Serializable
data class AutoCompleteShortcutDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("highlightRanges")
    val highlightRanges: List<HighlightRangeDto>,
)

@Serializable
data class RelatedKeywordDto(
    @SerialName("keywordId")
    val keywordId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("highlightRanges")
    val highlightRanges: List<HighlightRangeDto>,
)

@Serializable
data class HighlightRangeDto(
    @SerialName("start")
    val start: Int,
    @SerialName("end")
    val end: Int,
)
