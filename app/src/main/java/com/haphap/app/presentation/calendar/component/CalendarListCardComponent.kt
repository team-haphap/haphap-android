package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.chip.HapHapStatusChip
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun CalendarListCardComponent(
    titleText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(HapHapTheme.colors.white)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = titleText,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray800,
        )

        Text(
            text = titleText,
            style = HapHapTheme.typography.caption.r10,
            color = HapHapTheme.colors.gray500,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun CalendarListCardComponentPreview() {
    HapHapTheme {
        CalendarListCardComponent(
            titleText = "2026 신입 개발자 공개채용",
            modifier = Modifier.padding(16.dp),
        )
    }
}
