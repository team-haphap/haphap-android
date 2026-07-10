package com.haphap.app.presentation.calendar

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.calendar.component.CalendarDayItem
import com.haphap.app.presentation.calendar.component.CalendarListCardComponent
import com.haphap.app.presentation.calendar.component.HapHapCustomCalendar
import java.time.LocalDate

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
) {
    CalendarScreen(
        onCalendarDateClick = {},
        modifier = modifier,
    )
}

@Composable
private fun CalendarScreen(
    onCalendarDateClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        HapHapCustomCalendar(
            onClick = onCalendarDateClick,
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = HapHapTheme.colors.gray100)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(count = 5) {
                CalendarListCardComponent(
                    titleText = "넹",
                    stage = "넹",
                    participantCount = 2,
                    imageUrl = "",
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    HapHapTheme {
        CalendarScreen(
            onCalendarDateClick = {},
        )
    }
}
