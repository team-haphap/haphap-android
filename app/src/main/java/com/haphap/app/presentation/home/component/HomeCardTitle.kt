package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
fun HomeCardTitle(
    title: String,
    description: String,
    isMore: Boolean,
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = HapHapTheme.typography.subtitle.b20,
                color = HapHapTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = description,
                style = HapHapTheme.typography.caption.m12,
                color = HapHapTheme.colors.gray500,
            )
        }

        if (isMore) {
            Text(
                text = "더보기",
                style = HapHapTheme.typography.caption.m12,
                color = HapHapTheme.colors.gray300,
                modifier = Modifier
                    .noRippleClickable(onClick = onMoreClick)
                    .padding(horizontal = 6.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeCardTitlePreview() {
    HapHapTheme {
        Column(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            HomeCardTitle(
                title = "최근 결과가 올라온 공고",
                description = "지원자 결과가 활발하게 공유되고 있는 공고를 확인해요",
                isMore = false,
            )

            Spacer(modifier = Modifier.height(20.dp))

            HomeCardTitle(
                title = "오늘 발표 예상 공고",
                description = "과거 패턴을 바탕으로 오늘 발표 가능성이 높은 공고를 확인해요",
                isMore = true,
                onMoreClick = {},
            )
        }
    }
}