package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

private val dayLabels = listOf("일", "월", "화", "수", "목", "금", "토")

@Composable
fun DayLabelRow(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, top = 12.dp)
    ) {
        dayLabels.forEach { dayLabel ->
            key(dayLabel) {
                Text(
                    text = dayLabel,
                    color = HapHapTheme.colors.gray600,
                    style = HapHapTheme.typography.body.sb13,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
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
