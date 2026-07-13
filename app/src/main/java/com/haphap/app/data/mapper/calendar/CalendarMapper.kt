package com.haphap.app.data.mapper.calendar

import com.haphap.app.core.extensions.toLocalDate
import com.haphap.app.data.model.calendar.CalendarModel
import com.haphap.app.data.model.calendar.CalendarPostingModel
import com.haphap.app.data.remote.dto.calendar.CalendarDateDto
import com.haphap.app.data.remote.dto.calendar.CalendarPostingDto
import com.haphap.app.data.remote.dto.calendar.CalendarPostingsResponseDto
import com.haphap.app.data.remote.dto.calendar.CalendarResponseDto
import com.haphap.app.core.designsystem.type.PresentChanceType

fun CalendarResponseDto.toModel(): List<CalendarModel> = dates.map { it.toModel() }

fun CalendarPostingsResponseDto.toModel(): List<CalendarPostingModel> = postings.map { it.toModel() }

private fun CalendarDateDto.toModel(): CalendarModel =
    CalendarModel(
        date = date.toLocalDate(),
        likelihood = likelihood.toPresentChanceType(),
    )

private fun CalendarPostingDto.toModel(): CalendarPostingModel =
    CalendarPostingModel(
        id = postingId,
        title = title,
        stageName = stageName,
        likelihood = likelihood.toPresentChanceType(),
        participantCount = participantCount,
        logoImageUrl = logoImageUrl,
    )

private fun String.toPresentChanceType(): PresentChanceType = when (this) {
    "NONE" -> PresentChanceType.NONE
    "VERY_LOW" -> PresentChanceType.VERY_LOW
    "LOW" -> PresentChanceType.LOW
    "MEDIUM" -> PresentChanceType.MEDIUM
    "HIGH" -> PresentChanceType.HIGH
    "VERY_HIGH" -> PresentChanceType.VERY_HIGH
    else -> PresentChanceType.NONE
}
