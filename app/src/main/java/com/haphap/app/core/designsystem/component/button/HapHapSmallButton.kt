package com.haphap.app.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

/**
 * HapHap 공용 "취소 - 변경하기" 버튼 컴포넌트
 *
 * @param onCancelClick "취소" 버튼 클릭 시 실행할 콜백
 * @param onChangeClick "변경하기" 버튼 클릭 시 실행할 콜백
 * @param modifier Modifier
 */
@Composable
fun HapHapSmallButton(
    onCancelClick: () -> Unit,
    onChangeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(11.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = HapHapTheme.colors.gray100,
                    shape = RoundedCornerShape(8.dp),
                )
                .noRippleClickable(onClick = onCancelClick)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "취소",
                color = HapHapTheme.colors.gray400,
                style = HapHapTheme.typography.body.sb18,
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = HapHapTheme.colors.primary500,
                    shape = RoundedCornerShape(8.dp),
                )
                .noRippleClickable(onClick = onChangeClick)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "변경하기",
                color = HapHapTheme.colors.white,
                style = HapHapTheme.typography.body.sb18,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HapHapSmallButtonPreview() {
    HapHapTheme {
        Box(
            modifier = Modifier
                .width(298.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            HapHapSmallButton(
                onCancelClick = {},
                onChangeClick = {},
            )
        }
    }
}