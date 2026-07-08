package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HapHapCalendar(
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
){
    val pageCount = Int.MAX_VALUE
    val startPage = Int.MAX_VALUE / 2
    val baseMonth = remember { YearMonth.now() }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = startPage,
        pageCount = { pageCount },
    )
    val currentYearMonth = baseMonth.plusMonths((pagerState.currentPage - startPage).toLong())

    Column(modifier = modifier) {
        CalendarHeader(
            yearMonth = currentYearMonth,
            onDateClick = {},
            onBackClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            },
            onNextClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
        )

        DayLabelRow()

        HorizontalPager(
            state = pagerState,
        ) { page ->
            val yearMonth = remember(page) {
                baseMonth.plusMonths((page - startPage).toLong())
            }
            HapHapCalendarGrid(
                yearMonth = yearMonth,
                selectedDate = selectedDate,
                onClick = { day ->
                    selectedDate = day
                    onClick(day)
                },
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        CalendarBottom()
    }
}

@Preview(showBackground = true)
@Composable
private fun HapHapCalendarPreview() {
    HapHapTheme {
        Column(modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 50.dp)) {
            HapHapCalendar(onClick = {})
        }
    }
}
