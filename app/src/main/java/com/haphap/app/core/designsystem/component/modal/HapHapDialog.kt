package com.haphap.app.core.designsystem.component.modal

import android.graphics.drawable.VectorDrawable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
 */

@Composable
fun HapHapModal(
    content: String,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog (
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            decorFitsSystemWindows = true,
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = HapHapTheme.colors.white,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 21.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_alert_53),
                contentDescription = null,
                tint = HapHapTheme.colors.gray200,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content,
                style = HapHapTheme.typography.body.sb16,
                color = HapHapTheme.colors.gray700,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(11.dp),
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
private fun HapHapModalPreview() {
    HapHapTheme {
        HapHapModal(
            content = "이전에 등록한 결과가 있습니다.\n결과를 변경할까요?",
            onDismiss = {},
            onConfirmClick = {},
        )
    }
}