package com.haphap.app.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.extensions.toDateString
import com.haphap.app.data.repository.api.calendar.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<CalendarContract.SideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    private var calendarJob: Job? = null
    private var calendarPostingsJob: Job? = null

    fun updateSelectedDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        calendarPostings(date)
    }

    fun onCalendarCardClick(postingId: Int) {
        viewModelScope.launch {
            _sideEffect.send(CalendarContract.SideEffect.NavigateToJobDetail(postingId))
        }
    }

    fun calendar(yearMonth: YearMonth) {
        _uiState.update { it.copy(calendarUiState = CalendarUiState.Loading) }

        calendarJob?.cancel()
        calendarJob = viewModelScope.launch {
            calendarRepository.getCalendar(date = yearMonth.toDateString())
                .onSuccess { models ->
                    _uiState.update {
                        it.copy(
                            calendarUiState = CalendarUiState.Success(models),
                            calendarModel = models.toPersistentList(),
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable)
                    _uiState.update {
                        it.copy(calendarUiState = CalendarUiState.Failure(throwable.message ?: "오류"))
                    }
                }
        }
    }

    private fun calendarPostings(date: LocalDate) {
        _uiState.update { it.copy(calendarPostingsUiState = CalendarUiState.Loading) }

        calendarPostingsJob?.cancel()
        calendarPostingsJob = viewModelScope.launch {
            calendarRepository.getCalendarPostings(date = date.toDateString())
                .onSuccess { models ->
                    _uiState.update {
                        it.copy(
                            calendarPostingsUiState = CalendarUiState.Success(models),
                            calendarCardList = models.toPersistentList(),
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable)
                    _uiState.update {
                        it.copy(calendarPostingsUiState = CalendarUiState.Failure(throwable.message ?: "오류"))
                    }
                }
        }
    }
}
