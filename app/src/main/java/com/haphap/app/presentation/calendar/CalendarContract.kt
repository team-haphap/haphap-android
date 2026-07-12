package com.haphap.app.presentation.calendar

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.calendar.CalendarListCardModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

sealed interface CalendarContract {
    @Immutable
    data class State(
        val selectedDate: LocalDate? = null,
        val calendarCardList: ImmutableList<CalendarListCardModel> = persistentListOf(),
    )
}