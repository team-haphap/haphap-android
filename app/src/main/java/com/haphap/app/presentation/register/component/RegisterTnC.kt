package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun RegisterTnC(
    isNecessary: Boolean,
    context: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Icon (
            imageVector = ImageVector.vectorResource(
                id = if (checked) {
                    R.drawable.ic_check_list_selected_24
                } else {
                    R.drawable.ic_check_list_default_24
                }
            ),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.noRippleClickable(
                onClick = {onCheckedChange(!checked)}
            ),
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = if (isNecessary) "(필수)" else "(선택)",
            color = HapHapTheme.colors.gray600,
            style = HapHapTheme.typography.caption.sb12,
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = context,
            color = HapHapTheme.colors.gray600,
            style = HapHapTheme.typography.caption.r12,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterTnCPreview() {
    var checked1 by remember {mutableStateOf(false)}
    var checked2 by remember {mutableStateOf(true)}

    HapHapTheme {
        Column(
            modifier = Modifier
                .background(HapHapTheme.colors.white)
                .padding(all = 20.dp),
        ) {
            RegisterTnC(
                isNecessary = false,
                context = "같은 공고의 발표가 감지되면 알림을 받습니다.",
                checked = checked1,
                onCheckedChange = {checked1 = it}
            )
            RegisterTnC(
                isNecessary = true,
                context = "내 상태는 부여된 닉네임으로 익명 등록되며, 중복·허위 등록 방지를 위해 기기 단위의 최소 검증이 적용됩니다.",
                checked = checked2,
                onCheckedChange = {checked2 = it}
            )
        }
    }
}