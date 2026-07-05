package com.haphap.app.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.state.UiState

@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToSignUpComplete: (userName: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val kakaoLoginManager = remember { KakaoLoginManager() }

    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is UiState.Failure -> {
                Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                viewModel.consumeFailure()
            }
            is UiState.Success -> {
                // onLoginSuccess()
                navigateToSignUpComplete(state.data.name)
            }
            else -> Unit
        }
    }

    LoginScreen(
        onKakaoLoginClick = {
            kakaoLoginManager.login(
                context = context,
                onSuccess = { kakaoAccessToken ->
                    viewModel.kakaoLogin(kakaoAccessToken)
                },
                onCancel = {},
                onNetworkError = {
                    Toast.makeText(context, "네트워크 연결을 확인해 주세요.", Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(context, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                },
            )
        },
        modifier = modifier,
    )
}

@Composable
fun LoginScreen(
    onKakaoLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white)
            .padding(start = 20.dp, end = 20.dp, bottom = 13.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.img_logo),
                contentDescription = null,
                modifier = Modifier.size(width = 48.dp, height = 50.dp),
            )
            Image(
                painter = painterResource(R.drawable.img_text_logo),
                contentDescription = null,
                modifier = Modifier.size(width = 206.dp, height = 34.dp),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "합격 발표가 움직이는 순간",
            style = HapHapTheme.typography.body.m14,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "회원 서비스 이용을 위해 로그인해주세요.",
            style = HapHapTheme.typography.caption.r10,
            color = HapHapTheme.colors.gray600,
        )

        Spacer(modifier = Modifier.height(29.dp))

        Button(
            onClick = onKakaoLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp, bottom = 12.dp),
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
                modifier = Modifier.size(21.dp),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "카카오로 시작하기",
                style = HapHapTheme.typography.caption.sb12,
                color = HapHapTheme.colors.gray800,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    HapHapTheme {
        LoginScreen(
            onKakaoLoginClick = {},
        )
    }
}