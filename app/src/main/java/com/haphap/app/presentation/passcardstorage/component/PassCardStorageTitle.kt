package com.haphap.app.presentation.passcardstorage.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun PassCardStorageTitle(
    description: String,
    modifier: Modifier = Modifier,
    userName: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    ) {
        if (userName != null) {
            Text(
                text = "${userName}님의 합격을 축하드려요!",
                style = HapHapTheme.typography.subtitle.b22,
                color = HapHapTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.height(2.dp))
        }

        Text(
            text = description,
            style = HapHapTheme.typography.body.sb13,
            color = HapHapTheme.colors.gray400,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCardStorageHeaderPreview() {
    HapHapTheme {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            PassCardStorageTitle(
                description = "합격카드를 모았어요. 언제든 꺼내볼 수 있어요.",
            )

            PassCardStorageTitle(
                description = "기다려온 순간, 진심으로 축하드려요",
                userName = "김합합",
            )
        }
    }
}