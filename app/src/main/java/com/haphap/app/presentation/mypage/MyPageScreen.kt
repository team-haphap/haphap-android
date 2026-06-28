package com.haphap.app.presentation.mypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun MyPageRoute(
    modifier: Modifier = Modifier,
) {
    MyPageScreen(
        modifier = modifier,
    )
}

@Composable
private fun MyPageScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "MyPage Screen")
    }
}


@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    HapHapTheme {
        MyPageScreen()
    }
}
