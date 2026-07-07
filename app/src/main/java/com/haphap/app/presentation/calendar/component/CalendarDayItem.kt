package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R.drawable.ic_calendar_today
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType

sealed interface DayType {
    data class InMonth(val enabled: Boolean = true) : DayType
    data object OutMonth : DayType
    data class Today(val enabled: Boolean = true) : DayType
}

@Composable
fun CalendarDayItem(
    day: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 3.dp)
    ) {
//        Text(
//            text = day,
//
//        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarDayItemPreview() {
    HapHapTheme {
        CalendarDayItem(day = "1")
    }
}
