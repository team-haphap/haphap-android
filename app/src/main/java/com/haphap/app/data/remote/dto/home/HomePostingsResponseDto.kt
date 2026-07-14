package com.haphap.app.data.remote.dto.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomePostingsResponseDto(
    @SerialName("postings")
    val postings: List<HomeRecentPostingsDto>,
)

@Serializable
data class HomeRecentPostingsDto(
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
){
}