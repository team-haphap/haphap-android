package com.haphap.app.core.designsystem.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType

/**
 * 취소/확인 버튼이 있는 알림용 다이얼로그.
 *
 * @param content 다이얼로그 본문에 표시할 안내 문구
 * @param onDismiss 다이얼로그 바깥 영역 또는 "취소" 버튼 클릭 시 호출되는 콜백
 * @param onConfirmClick "확인" 버튼 클릭 시 호출되는 콜백
 * @param iconTrue 다이얼로그에 아이콘이 있는 지 설정하는 변수
 */

@Composable
fun HapHapDialog(
    content: String,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTrue: Boolean = false,
) {
    Dialog (
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 31.dp)
                .fillMaxWidth()
                .background(
                    color = HapHapTheme.colors.white,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(
                    start = 19.dp,
                    top = 16.dp,
                    end = 19.dp,
                    bottom = if (iconTrue) 21.dp else 16.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (iconTrue) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_alert_53),
                    contentDescription = null,
                    tint = HapHapTheme.colors.gray200,
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = content,
                style = HapHapTheme.typography.body.sb16,
                color = HapHapTheme.colors.gray700,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                HapHapBasicButton(
                    text = "취소",
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    textStyle = HapHapTheme.typography.body.sb18,
                    colorType = ButtonType.Cancel,
                )
                HapHapBasicButton(
                    text = "확인",
                    onClick = onConfirmClick,
                    modifier = Modifier.weight(1f),
                    textStyle = HapHapTheme.typography.body.sb18,
                    colorType = ButtonType.Primary(enabled = true),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun HapHapDialogPreview() {
    HapHapTheme {
        HapHapDialog(
            content = "로그아웃 하시겠습니까?",
            onDismiss = {},
            onConfirmClick = {},
            iconTrue = false,
        )
    }
}