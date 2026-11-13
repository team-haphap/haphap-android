package com.haphap.app.presentation.setting.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun SettingSectionDivider(
    modifier: Modifier = Modifier,
) {
    HorizontalDivider(
        color = HapHapTheme.colors.gray200,
        thickness = 1.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SettingSectionDividerPreview() {
    HapHapTheme {
        SettingSectionDivider()
    }
}
