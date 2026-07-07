package com.haphap.app.presentation.mypage.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun MyPageTopSection(
    nameText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Text(
            text = "${nameText}님의 마이페이지",
            color = HapHapTheme.colors.gray800,
            style = HapHapTheme.typography.subtitle.b22,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${nameText}님의 합격 여정을 합합이 응원할게요.",
            color = HapHapTheme.colors.gray500,
            style = HapHapTheme.typography.body.m14,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageTopSectionPreview() {
    HapHapTheme {
        MyPageTopSection(
            nameText = "익명의죠르디",
        )
    }
}
