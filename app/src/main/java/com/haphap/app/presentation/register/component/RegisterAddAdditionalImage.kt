package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.dashedBorder
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun RegisterAddAdditionalImage(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(HapHapTheme.colors.gray100)
            .dashedBorder(
                color = HapHapTheme.colors.gray200,
                cornerRadius = 12.dp,
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 73.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "이미지 추가",
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray800,
        )

        Text(
            text = "최대 3개까지 첨부 할 수 있습니다",
            style = HapHapTheme.typography.caption.sb12,
            color = HapHapTheme.colors.gray500,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterAddAdditionalImagePreview() {
    HapHapTheme {
        RegisterAddAdditionalImage(
            onClick = {},
        )
    }
}
