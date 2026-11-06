package com.haphap.app.presentation.mypage.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun ProfileTextFieldSection(
    state: TextFieldState,
    text: String,
    placeholder: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    val messageColor = if (isError) HapHapTheme.colors.subred else HapHapTheme.colors.gray300
    val nickname = state.text.toString().trim()

    val message = buildAnnotatedString {
        if (isError) {
            append("다른 사용자가 사용중인 닉네임이에요.")
        } else {
            append("다른 사용자에게는 ")
            withStyle(SpanStyle(color = HapHapTheme.colors.primary100)) {
                append("익명의 $nickname")
            }
            append(" 으로 보여요")
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.black,
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProfileTextField(
            state = state,
            placeholder = placeholder,
            isError = isError,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_profile_alert_16),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = messageColor,
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = message,
                style = HapHapTheme.typography.caption.sb10,
                color = messageColor,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileTextFieldSectionPreview() {
    HapHapTheme {
        Column {
            ProfileTextFieldSection(
                state = TextFieldState("박합합"),
                text = "닉네임",
                placeholder = "닉네임을 입력해주세요",
                isError = false,
            )

            ProfileTextFieldSection(
                state = TextFieldState("김합합"),
                text = "닉네임",
                placeholder = "닉네임을 입력해주세요",
                isError = true,
            )
        }
    }
}
