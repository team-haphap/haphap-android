package com.haphap.app.data.remote.dto.passcard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassCardUnconfirmedResponseDto(
    @SerialName("passCards")
    val passCards: List<PassCardUnconfirmedItemResponseDto>,
)

@Serializable
data class PassCardUnconfirmedItemResponseDto(
    @SerialName("registrationId")
    val registrationId: Int,
    @SerialName("userName")
    val userName: String,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("companyCardLogoImageUrl")
    val companyCardLogoImageUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("cardImageUrl")
    val cardImageUrl: String,
    @SerialName("approvedAt")
    val approvedAt: String,
)