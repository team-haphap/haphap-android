package com.haphap.app.data.remote.dto.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalendarResponseDto(
    @SerialName("dates")
    val dates: List<CalendarDateDto>,
)

@Serializable
data class CalendarDateDto(
    @SerialName("date")
    val date: String,
    @SerialName("likelihood")
    val likelihood: String,
)
