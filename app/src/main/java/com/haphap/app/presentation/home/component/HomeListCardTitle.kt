package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun HomeListCardTitle(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "오늘 발표 예상 공고",
            style = HapHapTheme.typography.subtitle.b20,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "과거 패턴을 바탕으로 오늘 발표 가능성이 높은 공고를 확인해요.",
            style = HapHapTheme.typography.caption.m12,
            color = HapHapTheme.colors.gray500,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeListCardTitlePreview() {
    HapHapTheme {
        HomeListCardTitle()
    }
}