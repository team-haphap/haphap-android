package com.haphap.app.presentation.leave.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.textfield.HapHapBasicTextField
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

private const val ETC_REASON_MAX_LENGTH = 200

@Composable
fun LeaveCheckList(
    context: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    isEtc: Boolean = false,
    etcReasonState: TextFieldState = rememberTextFieldState(),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(
                    onClick = { onCheckedChange(!checked) }
                ),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (checked) {
                        R.drawable.ic_check_list_selected_24
                    } else {
                        R.drawable.ic_check_list_default_24
                    }
                ),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = context,
                color = HapHapTheme.colors.gray600,
                style = HapHapTheme.typography.body.sb14,
            )
        }

        if (isEtc && checked) {
            Spacer(modifier = Modifier.height(2.dp))

            HapHapBasicTextField(
                state = etcReasonState,
                textColor = HapHapTheme.colors.gray800,
                textStyle = HapHapTheme.typography.caption.r12,
                placeholder = "최대 200자까지 입력이 가능해요.",
                placeholderColor = HapHapTheme.colors.gray300,
                placeholderStyle = HapHapTheme.typography.caption.r12,
                inputTransformation = InputTransformation.maxLength(ETC_REASON_MAX_LENGTH),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = HapHapTheme.colors.gray100)
                    .padding(start = 18.dp, top = 7.dp, bottom = 7.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun LeaveCheckListPreview() {
    var checked by remember { mutableStateOf(true) }

    HapHapTheme {
        LeaveCheckList(
            context = "기타 (직접 입력)",
            onCheckedChange = { checked = it },
            checked = checked,
            isEtc = true,
        )
    }
}
