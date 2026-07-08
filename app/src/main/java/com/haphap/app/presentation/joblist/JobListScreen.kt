package com.haphap.app.presentation.joblist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun JobListRoute(
    modifier: Modifier = Modifier,
) {
    JobListScreen(
        modifier = modifier,
    )
}

@Composable
private fun JobListScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Job Screen")
    }
}


@Preview(showBackground = true)
@Composable
private fun JobListScreenPreview() {
    HapHapTheme {
        JobListScreen()
    }
}
