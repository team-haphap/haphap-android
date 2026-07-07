package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.chip.HapHapStatusChip
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.StatusChipType

@Composable
fun HomeEmptyComponent(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(vertical = 47.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = text,
            style = HapHapTheme.typography.caption.sb12,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier) {
            Text(
                text = "하단의",
                style = HapHapTheme.typography.caption.sb12,
                color = HapHapTheme.colors.gray500,
            )

            Spacer(modifier = Modifier.width(2.dp))

            HapHapStatusChip(
                text = "등록",
                type = StatusChipType.CATEGORY,
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "탭에서 내 결과를 공유할 수 있어요!",
                style = HapHapTheme.typography.caption.sb12,
                color = HapHapTheme.colors.gray500,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeEmptyComponentPreview() {
    HapHapTheme {
        HomeEmptyComponent(
            text = "아직 등록된 지원 내역이 없어요",
        )
    }
}