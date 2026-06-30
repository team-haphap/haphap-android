package com.haphap.app.core.designsystem.component.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.haphap.app.core.designsystem.theme.HapHapTheme

/**
 * 텍스트 입력을 위한 기본 텍스트 필드 컴포넌트입니다.
 * 장식이 없는 순수 입력 영역을 제공하며, TextField UI 컴포넌트에서 입력 영역으로 함께 사용됩니다.
 *
 * @param state 텍스트 필드의 텍스트, 커서, 선택 상태를 관리하는 객체
 * @param textColor 입력된 텍스트의 색상
 * @param textStyle 입력된 텍스트의 서체 스타일
 * @param placeholder 입력값이 비어있을 때 보여줄 문구
 * @param placeholderStyle 힌트 텍스트 스타일
 * @param placeholderColor 힌트 텍스트 색상
 * @param isEnabled 텍스트 필드 활성화 여부 (false: 입력 불가 및 시각적 비활성화)
 * @param isReadOnly 읽기 전용 여부 (true: 입력 불가, 텍스트 선택 및 복사 가능)
 * @param keyboardOptions 키보드 옵션 설정 (키보드 종류, enter 키 아이콘 등)
 * @param onKeyboardAction  키보드의 Enter키 동작 방식 설정
 * @param lineLimits 줄 수 제한 (SingleLine 또는 MultiLine 설정)
 * @param inputTransformation 입력 시 state 변환 (길이 제한, 특정 문자 필터링)
 * @param outputTransformation 화면 표시 텍스트 변환 (텍스트 포맷팅)
 * @param interactionSource 컴포넌트의 상호작용 상태(Focus, Press 등)를 수집하는 통로
 * @param cursorColor 텍스트 커서의 색상
 * @param suffix 텍스트 필드 우측 끝에 배치될 추가 요소 (아이콘, 버튼 등)
 *
 */

@Composable
fun HapHapBasicTextField(
    state: TextFieldState,
    textColor: Color,
    textStyle: TextStyle,
    placeholder: String,
    placeholderColor: Color,
    placeholderStyle: TextStyle,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    interactionSource: MutableInteractionSource? = null,
    cursorColor: Color = textColor,
    suffix: (@Composable () -> Unit)? = null,
) {
    BasicTextField(
        state = state,
        modifier = modifier,
        enabled = isEnabled,
        readOnly = isReadOnly,
        textStyle = textStyle.copy(color = textColor),
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        lineLimits = lineLimits,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(cursorColor),
        decorator = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
            ){
                Box(
                    modifier = Modifier.weight(1f),
                ){
                    if (state.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = placeholderColor,
                            style = placeholderStyle,
                        )
                    }
                    innerTextField()
                }
                suffix?.invoke()
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun HapHapBasicTextFieldPreview() {
    val state = rememberTextFieldState(initialText = "")

    HapHapTheme{
        HapHapBasicTextField(
            state = state,
            textColor = HapHapTheme.colors.gray800,
            textStyle = HapHapTheme.typography.body.m14,
            placeholder = "공고명을 검색해보세요!",
            placeholderColor = HapHapTheme.colors.gray400,
            placeholderStyle = HapHapTheme.typography.caption.m12,
        )
    }

}
