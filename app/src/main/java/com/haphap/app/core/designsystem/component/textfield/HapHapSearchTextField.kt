package com.haphap.app.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R.drawable.ic_search_32
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

/**
 * 검색 텍스트 필드 공통 컴포넌트입니다.
 *
 * @param state 텍스트 필드의 상태 (입력값 및 커서 위치 관리)
 * @param onSearch 키보드 검색 버튼 클릭 시 동작
 *
 */

@Composable
fun HapHapSearchTextField(
    state: TextFieldState,
    placeholder: String,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions =  KeyboardOptions(imeAction = ImeAction.Search),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .background(
                color = HapHapTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(vertical = 5.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        HapHapBasicTextField(
            state = state,
            textColor = HapHapTheme.colors.gray800,
            textStyle = HapHapTheme.typography.body.m14,
            placeholder = placeholder,
            placeholderColor = HapHapTheme.colors.gray400,
            placeholderStyle = HapHapTheme.typography.caption.m12,
            modifier = Modifier.weight(1f),
            keyboardOptions = keyboardOptions,
            onKeyboardAction = {
                onSearch()
                focusManager.clearFocus()
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            interactionSource = interactionSource,
        )

        Icon(
            imageVector = ImageVector.vectorResource(ic_search_32),
            contentDescription = null,
            tint = HapHapTheme.colors.gray500,
            modifier = Modifier.noRippleClickable(
                onClick = {
                    onSearch()
                    focusManager.clearFocus()
                }
            ),
        )
    }
}


@Preview
@Composable
private fun HapHapSearchTextFieldPreview() {
    val state = rememberTextFieldState(initialText = "")
    HapHapTheme {
        HapHapSearchTextField(
            state = state,
            placeholder = "공고명을 검색해보세요!",
            onSearch = {},
            modifier = Modifier.padding(20.dp)
        )
    }
}
