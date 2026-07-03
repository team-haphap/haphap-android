package com.haphap.app.core.designsystem.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

/**
 * HapHap 공용 Large 버튼 컴포넌트
 *
 * @param text 버튼 안에 표시할 텍스트
 * @param enabled 클릭 가능 여부. true면 primary500, false면 gray300 색상 적용
 * @param onClick 클릭 시 실행할 콜백
 * @param modifier Modifier
 */
@Composable
fun HapHapLargeButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HapHapBasicButton(
        text = text,
        textStyle = HapHapTheme.typography.body.b18,
        colorType = HapHapButtonColorType.Primary(enabled = enabled),
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
private fun HapHapLargeButtonPreview() {
    HapHapTheme {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            HapHapLargeButton(
                text = "내 상태 등록",
                enabled = true,
                onClick = {},
            )

            Spacer(modifier = Modifier.height(20.dp))

            HapHapLargeButton(
                text = "내 상태 등록",
                enabled = false,
                onClick = {},
            )
        }
    }
}