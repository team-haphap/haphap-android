package com.haphap.app.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.extensions.toDateString
import com.haphap.app.core.state.UiState
import com.haphap.app.data.repository.api.calendar.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarContract.State())
    val uiState = _uiState.asStateFlow()

    private var calendarJob: Job? = null
    private var calendarPostingsJob: Job? = null

    fun updateSelectedDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        calendarPostings(date)
    }

    fun calendar(yearMonth: YearMonth) {
        _uiState.update { it.copy(calendarUiState = UiState.Loading) }

        calendarJob?.cancel()
        calendarJob = viewModelScope.launch {
            calendarRepository.getCalendar(date = yearMonth.toDateString())
                .onSuccess { models ->
                    _uiState.update {
                        it.copy(
                            calendarUiState = UiState.Success(models),
                            calendarModel = models.toPersistentList(),
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(calendarUiState = UiState.Failure(throwable.message ?: "오류"))
                    }
                }
        }
    }

    private fun calendarPostings(date: LocalDate) {
        _uiState.update { it.copy(calendarPostingsUiState = UiState.Loading) }

        calendarPostingsJob?.cancel()
        calendarPostingsJob = viewModelScope.launch {
            calendarRepository.getCalendarPostings(date = date.toDateString())
                .onSuccess { models ->
                    _uiState.update {
                        it.copy(
                            calendarPostingsUiState = UiState.Success(models),
                            calendarCardList = models.toPersistentList(),
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(calendarPostingsUiState = UiState.Failure(throwable.message ?: "오류"))
                    }
                }
        }
    }
}
