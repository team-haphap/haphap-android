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
        val calendarUiState: CalendarUiState<List<CalendarModel>> = CalendarUiState.Idle,
        val calendarPostingsUiState: CalendarUiState<List<CalendarPostingModel>> = CalendarUiState.Idle,
    )
}

sealed interface CalendarUiState<out T> {
    data object Idle : CalendarUiState<Nothing>
    data object Loading : CalendarUiState<Nothing>
    data class Success<T>(
        val data: T,
    ) : CalendarUiState<T>
    data class Failure(
        val msg: String,
    ) : CalendarUiState<Nothing>
}