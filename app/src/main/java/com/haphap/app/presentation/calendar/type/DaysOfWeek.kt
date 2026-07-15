package com.haphap.app.presentation.calendar.type

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.DayOfWeek

//뷰 조립할때 uiState에 적을게용
val defaultDaysOfWeek: ImmutableList<DayOfWeek> = persistentListOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY,
)
