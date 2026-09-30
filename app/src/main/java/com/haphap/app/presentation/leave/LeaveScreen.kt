package com.haphap.app.presentation.leave

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.presentation.leave.component.LeaveCheckList
import com.haphap.app.presentation.leave.component.LeaveTopBar

@Composable
private fun LeaveScreen(
    onBackClick: () -> Unit,
    onReasonClick: () -> Unit,
    onReasonEnter: () -> Unit,
    onLeaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        LeaveTopBar(
            onBackClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "탈퇴 유의사항",
            style = HapHapTheme.typography.caption.m12,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // TODO: 탈퇴 유의 사항 기입

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider(
            color = HapHapTheme.colors.gray200,
            thickness = 1.dp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "탈퇴 사유를 선택해주세요(필수)",
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
        )

        Spacer(modifier = Modifier.height(12.dp))

        LeaveCheckList(
            context = "취업 준비 활동이 끝났어요.",
            onCheckedChange = {},
        )

        LeaveCheckList(
            context = "원하는 정보가 부족해요.",
            onCheckedChange = {},
        )

        LeaveCheckList(
            context = "서비스를 잘 사용하지 않아요.",
            onCheckedChange = {},
        )

        LeaveCheckList(
            context = "개인정보(보안) 유출이 걱정돼요.",
            onCheckedChange = {},
        )

        LeaveCheckList(
            context = "기타 (직접 입력)",
            onCheckedChange = {},
            isEtc = true,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            HapHapBasicButton(
                text = "취소",
                onClick = onBackClick,
                modifier = Modifier.weight(1f),
                textStyle = HapHapTheme.typography.body.b18,
                colorType = ButtonType.Cancel,
            )
            HapHapBasicButton(
                text = "탈퇴하기",
                onClick = onLeaveClick,
                modifier = Modifier.weight(1f),
                textStyle = HapHapTheme.typography.body.b18,
                colorType = ButtonType.Primary(enabled = true),
            )
        }
    }
}