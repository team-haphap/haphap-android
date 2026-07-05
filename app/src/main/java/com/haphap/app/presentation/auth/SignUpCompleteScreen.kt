package com.haphap.app.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType

@Composable
fun SignUpCompleteRoute(
    modifier: Modifier = Modifier,
    userName: String,
    navigateToHome: () -> Unit,
) {
    SignUpCompleteScreen(
        userName = userName.ifBlank { "사용자" },
        navigateToHome = navigateToHome,
        modifier = modifier,
        )
}

@Composable
fun SignUpCompleteScreen(
    userName: String,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white)
            .padding(start = 20.dp, end = 20.dp, top = 43.dp),
    ) {
        Text(
            text = "${userName}님 가입을 완료했어요!",
            style = HapHapTheme.typography.subtitle.b22,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "지원자들의 결과를 확인하고 내 상태도 등록해보세요.",
            style = HapHapTheme.typography.body.m14,
            color = HapHapTheme.colors.gray500,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(172.dp))

            Image(
                painter = painterResource(R.drawable.img_logo),
                contentDescription = null,
                modifier = Modifier.size(width = 48.dp, height = 50.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(R.drawable.img_text_logo),
                contentDescription = null,
                modifier = Modifier.size(width = 191.dp, height = 30.dp),
            )
        }

        HapHapBasicButton(
            text = "시작하기",
            textStyle = HapHapTheme.typography.body.b18,
            colorType = ButtonType.Primary(enabled = true),
            onClick = navigateToHome,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpCompleteScreenPreview() {
    HapHapTheme {
        SignUpCompleteScreen(
            userName = "박연수",
            navigateToHome = {},
        )
    }
}