package com.haphap.app.data.remote.dto.alarm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlarmDeviceRequestDto(
    @SerialName("deviceId")
    val deviceId: String,
    @SerialName("fcmToken")
    val fcmToken: String,
    @SerialName("deviceType")
    val deviceType: String,
)
