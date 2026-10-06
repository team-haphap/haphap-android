package com.haphap.app.core.designsystem.component.chip

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
import com.haphap.app.core.designsystem.type.InfoChipType

/**
 * 정보칩 공통 컴포넌트입니다.
 *
 * chipText가 "마감"이면 type과 상관없이 CLOSED 스타일로 표시됩니다.
 *
 * @param chipText 칩에 표시할 텍스트
 * @param type 칩 타입
 */
@Composable
fun HapHapInfoChip(
    chipText: String,
    type: InfoChipType,
    modifier: Modifier = Modifier,
) {
    val (textColor, backgroundColor) = when (type) {
        InfoChipType.EXPECTED -> HapHapTheme.colors.primary100 to HapHapTheme.colors.sub100
        InfoChipType.COUNT -> HapHapTheme.colors.gray500 to HapHapTheme.colors.gray100
        InfoChipType.CLOSED -> HapHapTheme.colors.gray400 to HapHapTheme.colors.gray100
    }

    Text(
        text = chipText,
        style = HapHapTheme.typography.caption.sb12,
        color = textColor,
        modifier = modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = backgroundColor)
            .padding(horizontal = 6.dp),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun HapHapInfoChipPreview() {
    HapHapTheme {
        Row {
            HapHapInfoChip(
                chipText = "서류 발표 예상",
                type = InfoChipType.EXPECTED,
            )

            Spacer(modifier = Modifier.width(6.dp))

            HapHapInfoChip(
                chipText = "00명 참여중",
                type = InfoChipType.COUNT,
            )

            Spacer(modifier = Modifier.width(6.dp))

            HapHapInfoChip(
                chipText = "마감",
                type = InfoChipType.CLOSED,
            )
        }
    }
}
