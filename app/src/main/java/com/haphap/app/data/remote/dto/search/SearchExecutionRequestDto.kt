package com.haphap.app.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchExecutionRequestDto(
    @SerialName("keyword")
    val keyword: String,
)
