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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

/**
 * 상태칩 공통 컴포넌트입니다.
 *
 * 타입에 따라 텍스트 스타일이 변경되며, 배경은 sub100, 텍스트는 primary500으로 표시됩니다.
 *
 * @param text 표시할 텍스트
 * @param type 칩 타입 (CATEGORY: 직무, STAGE: 전형, COUNT: 인원)
 *
 */
@Composable
fun HapHapStatusChip(
    text: String,
    type: StatusChipType,
    modifier: Modifier = Modifier,
) {
    val textStyle: TextStyle = when (type) {
        StatusChipType.CATEGORY -> HapHapTheme.typography.caption.sb10
        StatusChipType.STAGE -> HapHapTheme.typography.caption.m12
        StatusChipType.COUNT -> HapHapTheme.typography.body.sb14
    }

    Text(
        text = text,
        style = textStyle,
        color = HapHapTheme.colors.primary500,
        modifier = modifier
            .clip(shape = CircleShape)
            .background(HapHapTheme.colors.sub100)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}

@Preview
@Composable
private fun HapHapStatusChipPreview() {
    HapHapTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            HapHapStatusChip(
                text = "개발",
                type = StatusChipType.CATEGORY,
            )
            HapHapStatusChip(
                text = "서류",
                type = StatusChipType.STAGE,
            )
            HapHapStatusChip(
                text = "+32명",
                type = StatusChipType.COUNT,
            )
        }
    }
}