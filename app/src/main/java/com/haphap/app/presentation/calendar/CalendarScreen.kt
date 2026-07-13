package com.haphap.app.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.calendar.CalendarPostingModel
import com.haphap.app.presentation.calendar.component.CalendarListCardComponent
import com.haphap.app.presentation.calendar.component.CalendarListCardEmptyComponent
import com.haphap.app.presentation.calendar.component.HapHapCustomCalendar
import com.haphap.app.core.designsystem.type.PresentChanceType
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalendarScreen(
        uiState = uiState,
        onCalendarDateClick = viewModel::updateSelectedDate,
        onCalendarCardClick = {},
        onCalendarMonthChange = viewModel::calendar,
        modifier = modifier,
    )
}

@Composable
private fun CalendarScreen(
    uiState: CalendarContract.State,
    onCalendarDateClick: (LocalDate) -> Unit,
    onCalendarCardClick: (Int) -> Unit,
    onCalendarMonthChange: (YearMonth) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        HapHapCustomCalendar(
            selectedDate = uiState.selectedDate,
            calendarModel = uiState.calendarModel,
            onClick = onCalendarDateClick,
            onMonthChange = onCalendarMonthChange,
        )

        if (uiState.calendarCardList.isEmpty()) {
            CalendarListCardEmptyComponent()
        } else {
            val listState = rememberSaveable(uiState.selectedDate, saver = LazyListState.Saver) { LazyListState() }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = HapHapTheme.colors.gray100)
                    .padding(horizontal = 20.dp),
                state = listState,
                contentPadding = PaddingValues(vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = uiState.calendarCardList,
                    key = { it.id },
                ) { card ->
                    CalendarListCardComponent(
                        titleText = card.title,
                        stage = card.stageName,
                        participantCount = card.participantCount,
                        imageUrl = card.logoImageUrl,
                        onClick = { onCalendarCardClick(card.id) }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    HapHapTheme {
        CalendarScreen(
            uiState = CalendarContract.State(
                calendarCardList = persistentListOf(
                    CalendarPostingModel(
                        id = 1,
                        title = "2026 신입 개발자 공개채용",
                        stageName = "서류",
                        likelihood = PresentChanceType.NONE,
                        participantCount = 32,
                        logoImageUrl = "",
                    ),
                    CalendarPostingModel(
                        id = 2,
                        title = "2026 신입 개발자 공개채용",
                        stageName = "서류",
                        likelihood = PresentChanceType.NONE,
                        participantCount = 32,
                        logoImageUrl = "",
                    ),
                    CalendarPostingModel(
                        id = 3,
                        title = "2026 신입 개발자 공개채용",
                        stageName = "서류",
                        likelihood = PresentChanceType.NONE,
                        participantCount = 32,
                        logoImageUrl = "",
                    ),
                    CalendarPostingModel(
                        id = 4,
                        title = "2026 신입 개발자 공개채용",
                        stageName = "서류",
                        likelihood = PresentChanceType.NONE,
                        participantCount = 32,
                        logoImageUrl = "",
                    ),
                    CalendarPostingModel(
                        id = 5,
                        title = "2026 신입 개발자 공개채용",
                        stageName = "서류",
                        likelihood = PresentChanceType.NONE,
                        participantCount = 32,
                        logoImageUrl = "",
                    ),
                ),
            ),
            onCalendarDateClick = {},
            onCalendarCardClick = {},
            onCalendarMonthChange = {},
        )
    }
}
