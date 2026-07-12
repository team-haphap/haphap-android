package com.haphap.app.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.calendar.CalendarListCardModel
import com.haphap.app.presentation.calendar.component.CalendarListCardComponent
import com.haphap.app.presentation.calendar.component.CalendarListCardEmptyComponent
import com.haphap.app.presentation.calendar.component.HapHapCustomCalendar
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalendarScreen(
        uiState = uiState,
        onCalendarDateClick = viewModel::updateSelectedDate,
        modifier = modifier,
    )
}

@Composable
private fun CalendarScreen(
    uiState: CalendarContract.State,
    onCalendarDateClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        HapHapCustomCalendar(
            selectedDate = uiState.selectedDate,
            onClick = onCalendarDateClick,
        )

        if (uiState.calendarCardList.isEmpty()) {
            CalendarListCardEmptyComponent()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = HapHapTheme.colors.gray100)
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = uiState.calendarCardList,
                    key = { it.id },
                ) { card ->
                    CalendarListCardComponent(
                        titleText = card.titleText,
                        stage = card.stage,
                        participantCount = card.participantCount,
                        imageUrl = card.imageUrl,
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
                    CalendarListCardModel(1, "2026 신입 개발자 공개채용", "서류", 32, ""),
                    CalendarListCardModel(2, "2026 신입 개발자 공개채용", "서류", 32, ""),
                    CalendarListCardModel(3, "2026 신입 개발자 공개채용", "서류", 32, ""),
                    CalendarListCardModel(4, "2026 신입 개발자 공개채용", "서류", 32, ""),
                    CalendarListCardModel(5, "2026 신입 개발자 공개채용", "서류", 32, ""),
                ),
            ),
            onCalendarDateClick = {},
        )
    }
}
