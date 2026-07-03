package com.haphap.app.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

/**
 * HapHap 공용 Small 버튼 컴포넌트
 *
 * 취소/변경하기 등 2개 버튼을 조합해서 사용할 때, 호출부에서 Row/Column 등으로
 * 자유롭게 배치할 수 있도록 단일 버튼 단위로 제공합니다.
 *
 * @param text 버튼 안에 표시할 텍스트
 * @param backgroundColor 버튼 배경색
 * @param textColor 텍스트 색상
 * @param onClick 클릭 시 실행할 콜백
 * @param modifier Modifier
 */
@Composable
fun HapHapSmallButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = textColor,
            style = HapHapTheme.typography.body.sb18,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HapHapSmallButtonPreview() {
    HapHapTheme {
        Row(
            modifier = Modifier
                .width(298.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp),
        ) {
            HapHapSmallButton(
                text = "취소",
                backgroundColor = HapHapTheme.colors.gray100,
                textColor = HapHapTheme.colors.gray400,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            HapHapSmallButton(
                text = "변경하기",
                backgroundColor = HapHapTheme.colors.primary500,
                textColor = HapHapTheme.colors.white,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
        }
    }
}