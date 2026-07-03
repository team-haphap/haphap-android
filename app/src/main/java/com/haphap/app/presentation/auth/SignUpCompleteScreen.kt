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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun SignUpCompleteRoute(
    modifier: Modifier = Modifier,
    userName: String,
    onStartClick: () -> Unit,
) {
    SignUpCompleteScreen(
        modifier = modifier,
        userName = userName.ifBlank { "사용자" },
        onStartClick = onStartClick
    )
}

@Composable
fun SignUpCompleteScreen(
    modifier: Modifier = Modifier,
    userName: String,
    onStartClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white)
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "${userName}님\n합합에 가입을 완료 했어요!",
            style = HapHapTheme.typography.title.b26,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${userName}님의 합격 여정을 합합이 함께 응원할게요",
            style = HapHapTheme.typography.body.r16,
            color = HapHapTheme.colors.gray500,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.img_logo),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(R.drawable.img_text_logo),
                contentDescription = null,
                modifier = Modifier.size(width = 140.dp, height = 23.dp),
            )
        }

        Column(
            modifier = Modifier.padding(bottom = 26.dp)
        ) {
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(49.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HapHapTheme.colors.primary500,
                    contentColor = HapHapTheme.colors.white,
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "시작하기",
                    style = HapHapTheme.typography.body.b18,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpCompleteScreenPreview() {
    HapHapTheme {
        SignUpCompleteScreen(
            userName = "박연수",
            onStartClick = {},
        )
    }
}