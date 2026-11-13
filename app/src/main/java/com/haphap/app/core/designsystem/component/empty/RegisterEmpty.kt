package com.haphap.app.core.designsystem.component.empty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.chip.HapHapStatusChip
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.StatusChipType

/**
 * 등록하기를 유도하는 빈 화면 공통 컴포넌트입니다.
 *
 * 상단에 [title]을, 하단에 "등록하기" 칩과 [description]을 한 줄로 보여줍니다.
 * [description]은 칩 뒤에 이어지는 문구이므로 "를 통해 ~" 처럼 칩에 붙는 형태로 작성합니다.
 *
 * @param title 빈 상태를 알리는 상단 문구
 * @param description "등록하기" 칩 뒤에 이어지는 하단 안내 문구
 */

@Composable
fun RegisterEmpty(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = HapHapTheme.colors.white),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = title,
            color = HapHapTheme.colors.gray400,
            style = HapHapTheme.typography.caption.sb12,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            HapHapStatusChip(
                text = "등록하기",
                type = StatusChipType.CATEGORY,
            )

            Text(
                text = description,
                color = HapHapTheme.colors.gray500,
                style = HapHapTheme.typography.caption.sb12,
            )
        }
    }
}

@Preview
@Composable
private fun RegisterEmptyPreview() {
    HapHapTheme {
        RegisterEmpty(
            title = "지원한 내역이 없어요.",
            description = "를 통해 지원 내역을 등록해보세요",
        )
    }
}
