package com.haphap.app.data.model.calendar

import com.haphap.app.core.designsystem.type.PresentChanceType
import java.time.LocalDate

data class CalendarModel(
    val date: LocalDate,
    val likelihood: PresentChanceType = PresentChanceType.NONE,
)
