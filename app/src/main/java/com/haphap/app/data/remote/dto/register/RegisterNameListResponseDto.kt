package com.haphap.app.data.remote.dto.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterNameListResponseDto(
    @SerialName("postings")
    val postings: List<RegisterNameDto>
)

@Serializable
data class RegisterNameDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
)