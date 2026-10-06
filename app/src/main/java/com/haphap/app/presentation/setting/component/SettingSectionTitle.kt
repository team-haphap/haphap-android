package com.haphap.app.presentation.setting.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun SettingSectionTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = HapHapTheme.typography.caption.m12,
        color = HapHapTheme.colors.gray400,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SettingSectionTitlePreview() {
    HapHapTheme {
        SettingSectionTitle(text = "계정")
    }
}
