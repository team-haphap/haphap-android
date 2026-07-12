package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.calendar.type.DayType
import com.haphap.app.presentation.calendar.type.defaultDaysOfWeek
import kotlinx.collections.immutable.ImmutableList
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarGrid(
    yearMonth: YearMonth,
    selectedDate: LocalDate?,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    daysOfWeek: ImmutableList<DayOfWeek> = defaultDaysOfWeek,
) {
    val firstDay = yearMonth.atDay(1)
    val dayIndex = daysOfWeek.indexOf(firstDay.dayOfWeek)
    val today = LocalDate.now()

    val weeks = remember(yearMonth, daysOfWeek) {
        (0 until 6 * 7).map { offset ->
            firstDay.plusDays((offset - dayIndex).toLong())
        }.chunked(7)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        weeks.forEach { week ->
            HorizontalDivider(
                thickness = 1.dp,
                color = HapHapTheme.colors.gray100,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                week.forEach { day ->
                    val dayType = if (YearMonth.from(day) == yearMonth) {
                        DayType.InMonth(
                            isToday = day == today,
                            isSelected = day == selectedDate,
                        )
                    } else {
                        DayType.OutMonth
                    }

                    CalendarDayItem(
                        day = day,
                        dayType = dayType,
                        onClick = { onClick(day) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarGridPreview() {
    HapHapTheme {
        CalendarGrid(
            yearMonth = YearMonth.now(),
            selectedDate = LocalDate.now(),
            onClick = {},
        )
    }
}