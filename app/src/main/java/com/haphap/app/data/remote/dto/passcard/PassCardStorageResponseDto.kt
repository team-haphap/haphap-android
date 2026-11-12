package com.haphap.app.data.remote.dto.passcard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassCardStorageResponseDto(
    @SerialName("cards")
    val cards: List<PassCardStorageItemResponseDto>,
)

@Serializable
data class PassCardStorageItemResponseDto(
    @SerialName("registrationId")
    val registrationId: Int,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("companyLogoImageUrl")
    val companyLogoImageUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("category")
    val category: String,
)