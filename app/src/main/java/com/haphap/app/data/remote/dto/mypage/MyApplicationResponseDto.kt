package com.haphap.app.data.remote.dto.mypage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyApplicationResponseDto(
    @SerialName("counts")
    val counts: MyApplicationCountsDto,
    @SerialName("registrations")
    val registrations: List<MyApplicationDto>,
)

@Serializable
data class MyApplicationCountsDto(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("inProgressCount")
    val inProgressCount: Int,
    @SerialName("allClosedCount")
    val allClosedCount: Int,
)

@Serializable
data class MyApplicationDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("title")
    val title: String,
    @SerialName("logoImageUrl")
    val logoImageUrl: String,
    @SerialName("currentState")
    val currentState: String?,
    @SerialName("myResult")
    val myResult: String,
)
