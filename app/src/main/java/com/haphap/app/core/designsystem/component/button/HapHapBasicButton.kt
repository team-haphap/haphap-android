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
import com.haphap.app.core.designsystem.theme.HapHapColors
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.HapHapButtonColorType
import com.haphap.app.core.extensions.noRippleClickable

private data class HapHapButtonStyle(
    val backgroundColor: Color,
    val textColor: Color,
    val isEnabled: Boolean,
)

private fun HapHapButtonColorType.toStyle(colors: HapHapColors): HapHapButtonStyle = when (this) {
    is HapHapButtonColorType.Primary -> HapHapButtonStyle(
        backgroundColor = if (enabled) colors.primary500 else colors.gray300,
        textColor = colors.white,
        isEnabled = enabled,
    )

    HapHapButtonColorType.Cancel -> HapHapButtonStyle(
        backgroundColor = colors.gray100,
        textColor = colors.gray400,
        isEnabled = true,
    )
}

/**
 * HapHap 공용 버튼의 기본 형태(base) 컴포넌트
 *
 * Large/Medium/Small 버튼은 모양(padding, radius, 클릭 처리)이 동일하고
 * 텍스트 스타일과 색상 조합만 다르므로 이 컴포넌트를 공통으로 사용합니다.
 *
 * @param text 버튼 안에 표시할 텍스트
 * @param textStyle 텍스트 스타일
 * @param colorType 버튼의 색상 조합 (Primary/Cancel)
 * @param onClick 클릭 시 실행할 콜백
 * @param modifier Modifier
 */
@Composable
fun HapHapBasicButton(
    text: String,
    textStyle: TextStyle,
    colorType: HapHapButtonColorType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val style = colorType.toStyle(HapHapTheme.colors)

    Box(
        modifier = modifier
            .background(
                color = style.backgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = onClick, isEnabled = style.isEnabled)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = style.textColor,
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
            textStyle = HapHapTheme.typography.body.b18,
            colorType = HapHapButtonColorType.Primary(),
            onClick = {},
        )
    }
}