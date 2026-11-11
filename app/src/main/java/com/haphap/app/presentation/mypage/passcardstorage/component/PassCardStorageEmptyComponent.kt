package com.haphap.app.presentation.mypage.passcardstorage.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun PassCardStorageEmptyComponent(
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "등록된 합격이 없어요.",
            style = HapHapTheme.typography.caption.sb12,
            color = HapHapTheme.colors.gray400,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            HapHapStatusChip(
                text = "등록하기",
                type = StatusChipType.CATEGORY,
                modifier = Modifier.noRippleClickable(onClick = onRegisterClick),
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "를 통해 합격 결과를 공유해보세요!",
                style = HapHapTheme.typography.caption.sb12,
                color = HapHapTheme.colors.gray500,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCardStorageEmptyComponentPreview() {
    HapHapTheme {
        PassCardStorageEmptyComponent(
            onRegisterClick = {},
        )
    }
}
