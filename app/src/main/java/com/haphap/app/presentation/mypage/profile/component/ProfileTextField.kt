package com.haphap.app.presentation.mypage.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.textfield.HapHapBasicTextField
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun ProfileTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    inputTransformation: InputTransformation? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val backgroundColor = when {
        isError -> HapHapTheme.colors.subred2
        isFocused -> HapHapTheme.colors.sub100
        else -> HapHapTheme.colors.gray50
    }
    val borderColor = when {
        isError -> HapHapTheme.colors.subred
        isFocused -> HapHapTheme.colors.primary100
        else -> HapHapTheme.colors.gray100
    }

    HapHapBasicTextField(
        state = state,
        textColor = HapHapTheme.colors.black,
        textStyle = HapHapTheme.typography.body.m14,
        placeholder = "",
        placeholderColor = HapHapTheme.colors.black,
        placeholderStyle = HapHapTheme.typography.body.m14,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(all = 10.dp),
        lineLimits = TextFieldLineLimits.SingleLine,
        inputTransformation = inputTransformation,
        interactionSource = interactionSource,
    )
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun ProfileTextFieldPreview() {
    HapHapTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ProfileTextField(
                state = rememberTextFieldState("김합합"),
            )

            ProfileTextField(
                state = rememberTextFieldState("김합합"),
                isError = true,
            )
        }
    }
}