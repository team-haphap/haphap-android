package com.haphap.app.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

/**
 * 마감(디데이) 상태칩 공통 컴포넌트입니다.
 *
 * "{전형명} 발표 D-{디데이}" 형태로 표시되며,
 * 전형명 부분은 gray600, 디데이 부분은 primary500으로 표시됩니다.
 *
 * @param stage 전형명 (예: 서류, 최종)
 * @param dDay 발표까지 남은 일수
 *
 */
@Composable
fun HapHapDeadlineChip(
    stage: String,
    dDay: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(HapHapTheme.colors.white)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$stage 발표",
            style = HapHapTheme.typography.caption.m10,
            color = HapHapTheme.colors.gray600,
        )
        Text(
            text = dDay,
            style = HapHapTheme.typography.caption.m10,
            color = HapHapTheme.colors.primary500,
        )
    }
}

@Preview
@Composable
private fun HapHapDeadlineChipPreview() {
    HapHapTheme {
        Row(modifier = Modifier.padding(16.dp)) {
            HapHapDeadlineChip(
                stage = "서류",
                dDay = "D-2",
            )
        }
    }
}
