package com.haphap.app.presentation.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun CommunityRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CommunityScreen(
        onBackClick = {},
        modifier = modifier,
    )
}

@Composable
private fun CommunityScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = "커뮤니티"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunityScreenPreview() {
    HapHapTheme {
        CommunityScreen(
            onBackClick = {},
        )
    }
}
