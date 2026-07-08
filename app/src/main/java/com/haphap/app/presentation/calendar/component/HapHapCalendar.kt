package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.calendar.type.DayType
import com.haphap.app.presentation.calendar.type.PresentChance
import com.haphap.app.presentation.calendar.type.defaultDaysOfWeek
import kotlinx.collections.immutable.ImmutableList
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

//@Composable
//fun HapHapCalendar(
//    onClick: (LocalDate) -> Unit,
//){
//    HorizontalPager() {
//        HapHapCalendarGrid(
//            onClick = onClick,
//        )
//    }
//}
@Composable
private fun HapHapCalendarGrid(
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    daysOfWeek: ImmutableList<DayOfWeek> = defaultDaysOfWeek,
) {
    val yearMonth = YearMonth.of(2026, 7)
    val firstDay = yearMonth.atDay(1)
    val dayIndex = daysOfWeek.indexOf(firstDay.dayOfWeek)
    val today = LocalDate.now()
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val weeks = remember(yearMonth, daysOfWeek) {
        (0 until 6 * 7).map { offset ->
            firstDay.plusDays((offset - dayIndex).toLong())
        }.chunked(7)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        weeks.forEach { week ->
            HorizontalDivider(
                thickness = 1.dp,
                color = HapHapTheme.colors.gray100
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                week.forEach { day ->
                    // TODO: presentChance 색상 확인용 임시 매핑, 실제 데이터 연결되면 제거
                    val testPresentChance = when (day.dayOfMonth) {
                        2 -> PresentChance.VERY_LOW
                        4 -> PresentChance.LOW
                        6 -> PresentChance.MEDIUM
                        8 -> PresentChance.HIGH
                        10 -> PresentChance.VERY_HIGH
                        else -> PresentChance.NONE
                    }

                    val dayType = if (YearMonth.from(day) == yearMonth) {
                        DayType.InMonth(
                            presentChance = testPresentChance,
                            isToday = day == today,
                            isSelected = day == selectedDate,
                        )
                    } else {
                        DayType.OutMonth
                    }

                    CalendarDayItem(
                        day = day,
                        dayType = dayType,
                        onClick = {
                            selectedDate = day
                            onClick(day)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HapHapCalendarPreview() {
    HapHapTheme {
        Column(modifier = Modifier.padding(top = 50.dp)) {
            HapHapCalendarGrid(onClick = {})
        }
    }
}
