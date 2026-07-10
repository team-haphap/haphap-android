package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.calendar.type.defaultDaysOfWeek
import kotlinx.collections.immutable.ImmutableList
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayLabelRow(
    modifier: Modifier = Modifier,
    daysOfWeek: ImmutableList<DayOfWeek> = defaultDaysOfWeek,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, top = 12.dp),
    ) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                color = HapHapTheme.colors.gray600,
                style = HapHapTheme.typography.body.sb13,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DayLabelRowPreview() {
    HapHapTheme {
        DayLabelRow()
    }
}
