package com.haphap.app.data.remote.dto.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto (
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("stageId")
    val stageId: Int,
    @SerialName("contactedDate")
    val contactedDate: String? = null,
    @SerialName("contactedTime")
    val contactedTime: String? = null,
    @SerialName("contactMethods")
    val contactMethods: List<String>? = null,
    @SerialName("result")
    val result: String,
    @SerialName("anonymous")
    val anonymous: Boolean,
    @SerialName("alarmEnabled")
    val alarmEnabled: Boolean,
)