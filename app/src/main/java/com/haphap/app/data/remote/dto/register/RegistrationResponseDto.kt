package com.haphap.app.data.remote.dto.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponseDto(
    @SerialName("registrationId")
    val registrationId: Int,
    @SerialName("card")
    val card: PassCardDto? = null,
)

@Serializable
data class PassCardDto(
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
)