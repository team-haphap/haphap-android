package com.haphap.app.data.remote.dto.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeAnnouncementsResponseDto(
    @SerialName("postings")
    val postings: List<HomeAnnouncementsDto>,
)

@Serializable
data class HomeAnnouncementsDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("category")
    val category: String,
    @SerialName("stageName")
    val stageName: String,
    @SerialName("imageUrl")
    val imageUrl: String,
)