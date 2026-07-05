package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun RegisterResultConfirm(
    recruitName: String,
    recruitProcess: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = HapHapTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(all = 20.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        Column {
            Text(
                text = "지원 정보",
                color = HapHapTheme.colors.gray400,
                style = HapHapTheme.typography.caption.m12,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = recruitName,
                color = HapHapTheme.colors.gray600,
                style = HapHapTheme.typography.body.sb14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = recruitProcess,
            modifier = Modifier
                .background(HapHapTheme.colors.white, RoundedCornerShape(8.dp))
                .padding(horizontal = 9.dp, vertical = 7.dp),
            color = HapHapTheme.colors.gray500,
            style = HapHapTheme.typography.caption.sb12,
        )
    }
}

@Preview
@Composable
private fun RegisterResultConfirmPreview() {
    HapHapTheme {
        Row(
            modifier = Modifier
                .background(HapHapTheme.colors.white)
                .padding(all = 20.dp)
        ) {
            RegisterResultConfirm(
                recruitName = "카카오 2026 신입 개발자 공개 채용",
                recruitProcess = "코딩 테스트",
            )
        }
    }
}