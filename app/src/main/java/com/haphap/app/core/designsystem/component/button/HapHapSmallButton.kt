package com.haphap.app.core.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

/**
 * HapHap 공용 Small 버튼 컴포넌트
 *
 * 취소/변경하기 등 2개 버튼을 조합해서 사용할 때, 호출부에서 Row/Column 등으로
 * 자유롭게 배치할 수 있도록 단일 버튼 단위로 제공합니다.
 *
 * @param text 버튼 안에 표시할 텍스트
 * @param colorType 버튼의 색상 조합 (Primary/Cancel)
 * @param onClick 클릭 시 실행할 콜백
 * @param modifier Modifier
 */
@Composable
fun HapHapSmallButton(
    text: String,
    colorType: HapHapButtonColorType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HapHapBasicButton(
        text = text,
        textStyle = HapHapTheme.typography.body.sb18,
        colorType = colorType,
        onClick = onClick,
        modifier = modifier,
    )
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
                colorType = HapHapButtonColorType.Cancel,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            HapHapSmallButton(
                text = "변경하기",
                colorType = HapHapButtonColorType.Primary(),
                onClick = {},
                modifier = Modifier.weight(1f),
            )
        }
    }
}