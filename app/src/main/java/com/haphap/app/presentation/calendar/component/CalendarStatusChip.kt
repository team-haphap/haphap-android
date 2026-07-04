package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun CalendarStatusChip(
    chipText: String,
    isExpectedStage: Boolean,
    modifier: Modifier = Modifier,
) {
    Text(
        text = chipText,
        style = HapHapTheme.typography.caption.sb12,
        color = if (isExpectedStage) HapHapTheme.colors.primary100 else HapHapTheme.colors.gray500,
        modifier = modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = if (isExpectedStage) HapHapTheme.colors.sub100 else HapHapTheme.colors.gray100)
            .padding(horizontal = 6.dp),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun CalendarStatusChipPreview() {
    HapHapTheme {
        Row() {
            CalendarStatusChip(
                chipText = "서류 발표 예상",
                isExpectedStage = true,
            )

            Spacer(modifier = Modifier.width(6.dp))

            CalendarStatusChip(
                chipText = "00명 참여중",
                isExpectedStage = false,
            )
        }
    }
}
