package com.haphap.app.presentation.setting.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun SettingMenuItem(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = HapHapTheme.colors.gray700,
    onClick: () -> Unit = {},
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 10.dp),
        style = HapHapTheme.typography.body.sb14,
        color = textColor,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SettingMenuItemPreview() {
    HapHapTheme {
        SettingMenuItem(text = "계정 정보")
    }
}
