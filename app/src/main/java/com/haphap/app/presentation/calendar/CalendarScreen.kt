package com.haphap.app.presentation.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
) {
    CalendarScreen(
        modifier = modifier,
    )
}

@Composable
private fun CalendarScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Calendar Screen")
    }
}


@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    HapHapTheme {
        CalendarScreen()
    }
}
