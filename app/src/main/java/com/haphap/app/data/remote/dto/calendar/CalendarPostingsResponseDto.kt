package com.haphap.app.data.remote.dto.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalendarPostingsResponseDto(
    @SerialName("date")
    val date: String,
    @SerialName("postings")
    val postings: List<CalendarPostingDto>,
)

@Serializable
data class CalendarPostingDto(
    @SerialName("postingId")
    val postingId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("stageName")
    val stageName: String,
    @SerialName("likelihood")
    val likelihood: String,
    @SerialName("participantCount")
    val participantCount: Int,
    @SerialName("logoImageUrl")
    val logoImageUrl: String,
)