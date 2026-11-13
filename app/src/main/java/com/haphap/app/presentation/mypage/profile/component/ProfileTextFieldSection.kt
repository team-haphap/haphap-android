package com.haphap.app.presentation.mypage.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.InputTransformation
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
import com.haphap.app.presentation.mypage.profile.type.ProfileNicknameStatus

@Composable
fun ProfileTextFieldSection(
    state: TextFieldState,
    text: String,
    nicknameStatus: ProfileNicknameStatus,
    modifier: Modifier = Modifier,
    inputTransformation: InputTransformation? = null,
) {
    val isError = nicknameStatus != ProfileNicknameStatus.DEFAULT
    val messageColor = if (isError) HapHapTheme.colors.subred else HapHapTheme.colors.gray300
    val nickname = state.text.toString().trim()

    val message = buildAnnotatedString {
        when (nicknameStatus) {
            ProfileNicknameStatus.DEFAULT -> {
                append("다른 사용자에게는 ")
                withStyle(SpanStyle(color = HapHapTheme.colors.primary100)) {
                    append("익명의 $nickname")
                }
                append(" 으로 보여요")
            }
            ProfileNicknameStatus.INVALID_CHARACTER -> append("한글, 영문, 숫자만 사용할 수 있어요.")
            ProfileNicknameStatus.DUPLICATED -> append("다른 사용자가 사용중인 닉네임이에요.")
            ProfileNicknameStatus.TOO_LONG -> append("5자 이내로 입력해주세요.")
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = text,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.black,
        )

        ProfileTextField(
            state = state,
            isError = isError,
            inputTransformation = inputTransformation,
        )

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
                nicknameStatus = ProfileNicknameStatus.DUPLICATED,
            )

            ProfileTextFieldSection(
                state = TextFieldState("김합합"),
                text = "닉네임",
                nicknameStatus = ProfileNicknameStatus.DEFAULT,
            )
        }
    }
}
