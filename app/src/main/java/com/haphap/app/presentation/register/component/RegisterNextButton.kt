package com.haphap.app.presentation.register.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType

@Composable
fun RegisterNextButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "다음",
) {
    HapHapBasicButton(
        text = text,
        textStyle = HapHapTheme.typography.body.b18,
        colorType = ButtonType.Primary(enabled = isEnabled),
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
    )
}