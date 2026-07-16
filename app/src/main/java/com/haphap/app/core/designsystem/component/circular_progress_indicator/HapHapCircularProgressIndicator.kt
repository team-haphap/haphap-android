package com.haphap.app.core.designsystem.component.circular_progress_indicator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun HapHapCircularProgressIndicator(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = HapHapTheme.colors.gray500,
        )
    }
}

@Preview
@Composable
private fun HapHapCircularProgressIndicatorPreview() {
    HapHapTheme {
        HapHapCircularProgressIndicator()
    }
}