package com.haphap.app.data.remote.dto.mypage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyRegistrationsPreviewResponseDto(
    @SerialName("registrations")
    val registrations: List<MyRegistrationPreviewDto>,
)

@Serializable
data class MyRegistrationPreviewDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("currentState")
    val currentState: String,
    @SerialName("applicantCount")
    val applicantCount: Int,
    @SerialName("logoImageUrl")
    val logoImageUrl: String,
    @SerialName("appliedAt")
    val appliedAt: String,
)
