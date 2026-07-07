package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun HomeRecentCardTitle(
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "최근 결과가 올라온 공고",
                style = HapHapTheme.typography.subtitle.b20,
                color = HapHapTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "지원자 결과가 활발하게 공유되고 있는 공고를 확인해요",
                style = HapHapTheme.typography.caption.m12,
                color = HapHapTheme.colors.gray500,
            )
        }
        Text(
            text = "더보기",
            style = HapHapTheme.typography.caption.m12,
            color = HapHapTheme.colors.gray300,
            modifier = Modifier.noRippleClickable(onClick = onMoreClick)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeRecentCardTitlePreview() {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
        HapHapTheme {
            HomeRecentCardTitle(
                onMoreClick = {},
            )
        }
    }
}