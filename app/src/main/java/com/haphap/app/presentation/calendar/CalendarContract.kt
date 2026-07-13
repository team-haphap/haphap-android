package com.haphap.app.presentation.calendar

import androidx.compose.runtime.Immutable
import com.haphap.app.core.state.UiState
import com.haphap.app.data.model.calendar.CalendarPostingModel
import com.haphap.app.data.model.calendar.CalendarModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

sealed interface CalendarContract {
    @Immutable
    data class State(
        val selectedDate: LocalDate? = null,
        val calendarModel: ImmutableList<CalendarModel> = persistentListOf(),
        val calendarCardList: ImmutableList<CalendarPostingModel> = persistentListOf(),
        val calendarUiState: CalendarUiState = CalendarUiState.Idle,
        val calendarPostingsUiState: CalendarUiState = CalendarUiState.Idle,
    )
}

sealed interface CalendarUiState {
    data object Idle : CalendarUiState
    data object Loading : CalendarUiState
    data object Success : CalendarUiState
    data class Failure(
        val msg: String,
    ) : CalendarUiState
}