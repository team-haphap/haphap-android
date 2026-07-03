package com.haphap.app.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

/**
 * HapHap 공용 버튼의 기본 형태(base) 컴포넌트
 *
 * Large/Medium/Small 버튼은 모양(padding, radius, 클릭 처리)이 동일하고
 * 색상, 텍스트, 텍스트 스타일만 다르므로 이 컴포넌트를 공통으로 사용합니다.
 *
 * @param text 버튼 안에 표시할 텍스트
 * @param textColor 텍스트 색상
 * @param textStyle 텍스트 스타일
 * @param backgroundColor 버튼 배경색
 * @param onClick 클릭 시 실행할 콜백
 * @param modifier Modifier
 * @param isEnabled 클릭 가능 여부
 */
@Composable
fun HapHapBasicButton(
    text: String,
    textColor: Color,
    textStyle: TextStyle,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = onClick, isEnabled = isEnabled)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HapHapBasicButtonPreview() {
    HapHapTheme {
        HapHapBasicButton(
            text = "확인",
            textColor = HapHapTheme.colors.white,
            textStyle = HapHapTheme.typography.body.b18,
            backgroundColor = HapHapTheme.colors.primary500,
            onClick = {},
        )
    }
}