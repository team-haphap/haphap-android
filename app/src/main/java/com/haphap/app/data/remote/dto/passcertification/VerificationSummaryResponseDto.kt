package com.haphap.app.data.remote.dto.passcertification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerificationSummaryResponseDto(
    @SerialName("pendingCount")
    val pendingCount: Int,
    @SerialName("approvedCount")
    val approvedCount: Int,
    @SerialName("rejectedCount")
    val rejectedCount: Int,
    @SerialName("items")
    val items: List<VerificationItemResponseDto>,
)

@Serializable
data class VerificationItemResponseDto(
    @SerialName("registrationId")
    val registrationId: Long,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("companyLogoImageUrl")
    val companyLogoImageUrl: String? = null,
    @SerialName("postingTitle")
    val postingTitle: String,
    @SerialName("stageName")
    val stageName: String,
    @SerialName("status")
    val status: String,
    @SerialName("submittedAt")
    val submittedAt: String? = null,
    @SerialName("resolvedAt")
    val resolvedAt: String? = null,
    @SerialName("resubmittable")
    val resubmittable: Boolean,
)
