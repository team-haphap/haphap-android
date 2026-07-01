package com.haphap.app.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onKakaoLoginClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "로그인",
            modifier = Modifier.padding(start = 20.dp, top = 10.dp),
            style = HapHapTheme.typography.subtitle.b20,
            color = HapHapTheme.colors.gray800,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.img_logo),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                )
                Image(
                    painter = painterResource(R.drawable.img_text_logo),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "합격 발표가 움직이는 순간",
                style = HapHapTheme.typography.body.m14,
                color = HapHapTheme.colors.gray400,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp, bottom = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "회원 서비스 이용을 위해 로그인해주세요.",
                style = HapHapTheme.typography.caption.r10,
                color = HapHapTheme.colors.gray600,
            )

            Spacer(modifier = Modifier.height(17.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    color = HapHapTheme.colors.primary500,
                )
            } else {
                Button(
                    onClick = onKakaoLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HapHapTheme.colors.yellow,
                        contentColor = HapHapTheme.colors.gray800,
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_login_kakao_logo),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "카카오로 시작하기",
                        style = HapHapTheme.typography.caption.sb12,
                        color = HapHapTheme.colors.gray800,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    HapHapTheme {
        LoginScreen(
            isLoading = false,
            onKakaoLoginClick = {},
        )
    }
}