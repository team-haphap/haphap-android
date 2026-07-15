package com.haphap.app.data.model.calendar

import com.haphap.app.core.designsystem.type.PresentChanceType

data class CalendarPostingModel(
    val id: Int,
    val title: String,
    val stageName: String,
    val likelihood: PresentChanceType,
    val participantCount: Int,
    val logoImageUrl: String,
)
