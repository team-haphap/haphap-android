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
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.core.extensions.noRippleClickable

/**
 * HapHap 버튼의 색상 스타일 정보
 *
 * 활성화 상태의 색상만 필수로 받고, 비활성화 상태의 색상은 기본값으로
 * 활성화 상태와 동일하게 설정됩니다. 비활성화 상태에서 다른 색상을 쓰고 싶은
 * 경우에만 [disabledBackgroundColor], [disabledTextColor]를 별도로 지정하면 됩니다.
 *
 * @param backgroundColor 활성화 상태의 배경색
 * @param textColor 활성화 상태의 텍스트 색상
 * @param disabledBackgroundColor 비활성화 상태의 배경색 (기본값: [backgroundColor]와 동일)
 * @param disabledTextColor 비활성화 상태의 텍스트 색상 (기본값: [textColor]와 동일)
 */
private data class HapHapButtonStyle(
    val backgroundColor: Color,
    val textColor: Color,
    val disabledBackgroundColor: Color = backgroundColor,
    val disabledTextColor: Color = textColor,
)

/**
 * [ButtonType]을 실제 색상 값([HapHapButtonStyle])으로 변환합니다.
 *
 * 활성화 여부에 따른 분기는 더 이상 이 함수에서 처리하지 않으며,
 * 각 색상 타입이 가질 수 있는 활성/비활성 색상 값만 그대로 전달합니다.
 */
private fun ButtonType.toStyle(colors: HapHapColors): HapHapButtonStyle = when (this) {
    is ButtonType.Primary -> HapHapButtonStyle(
        backgroundColor = colors.primary500,
        textColor = colors.white,
        disabledBackgroundColor = colors.gray300,
    )

    ButtonType.Cancel -> HapHapButtonStyle(
        backgroundColor = colors.gray100,
        textColor = colors.gray400,
    )

    ButtonType.Selected -> HapHapButtonStyle(
        backgroundColor = colors.sub100,
        textColor = colors.primary500,
    )

    ButtonType.Default -> HapHapButtonStyle(
        backgroundColor = colors.gray100,
        textColor = colors.gray400,
    )
}

/**
 * HapHap 공용 버튼의 기본 형태(base) 컴포넌트
 *
 * Large/Medium/Small 버튼은 모양(padding, radius, 클릭 처리)이 동일하고
 * 텍스트 스타일과 색상 조합만 다르므로 이 컴포넌트를 공통으로 사용합니다.
 *
 * 활성화 여부는 [colorType]으로부터 판단하며, 이에 따라 활성/비활성
 * 배경색과 텍스트 색상, 클릭 가능 여부가 결정됩니다.
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
    colorType: ButtonType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val style = colorType.toStyle(HapHapTheme.colors)
    val isEnabled = when (colorType) {
        is ButtonType.Primary -> colorType.enabled
        ButtonType.Cancel -> true
        ButtonType.Selected -> true
        ButtonType.Default -> true
    }

    Box(
        modifier = modifier
            .background(
                color = if (isEnabled) style.backgroundColor else style.disabledBackgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = onClick, isEnabled = isEnabled)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = if (isEnabled) style.textColor else style.disabledTextColor,
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
            colorType = ButtonType.Primary(enabled = false),
            onClick = {},
        )
    }
}