package com.haphap.app.data.remote.dto.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobDetailDto(
    @SerialName("companyName") val companyName: String,
    @SerialName("postingTitle") val postingTitle: String,
    @SerialName("category") val category: String,
    @SerialName("location") val location: String,
    @SerialName("position") val position: String,
    @SerialName("currentState") val currentState: String,
    @SerialName("companyImageUrl") val companyImageUrl: String,
    @SerialName("summary") val summary: JobDetailSummaryDto,
    @SerialName("registrations") val registrations: List<JobDetailRegistrationDto>,
)

@Serializable
data class JobDetailSummaryDto(
    @SerialName("registeredCount") val registeredCount: Long,
    @SerialName("profileImages") val profileImages: List<ParticipantProfileDto>,
    @SerialName("additionalParticipantCount") val additionalParticipantCount: Long,
)

@Serializable
data class ParticipantProfileDto(
    @SerialName("userId") val userId: Long,
    @SerialName("profileImageUrl") val profileImageUrl: String,
)

@Serializable
data class JobDetailRegistrationDto(
    @SerialName("registrationId") val registrationId: Long,
    @SerialName("feedCreatedAt") val feedCreatedAt: String,
    @SerialName("nickName") val nickName: String,
    @SerialName("registrationResult") val registrationResult: String,
    @SerialName("stage") val stage: String,
)