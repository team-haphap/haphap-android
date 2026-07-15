package com.haphap.app.presentation.auth.login

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.presentation.auth.login.LoginContract.SideEffect.NavigateToSignUpComplete
import com.haphap.app.presentation.auth.login.LoginContract.SideEffect.OnShowToast
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.circular_progress_indicator.HapHapCircularProgressIndicator
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun LoginRoute(
    navigateToSignUpComplete: (userName: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val kakaoLoginManager = remember { KakaoLoginManager() }
    val lifeCycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifeCycleOwner) {
        lifeCycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToSignUpComplete -> {
                        navigateToSignUpComplete(sideEffect.userName)
                    }
                    is OnShowToast -> {
                        Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onKakaoLoginClick = {
            viewModel.onKakaoLoginButtonClick()
            kakaoLoginManager.login(
                context = context,
                onSuccess = { kakaoAccessToken ->
                    viewModel.kakaoLogin(kakaoAccessToken)
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
    uiState: LoginContract.State,
    onKakaoLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState.loginUiState) {
        LoginUiState.Loading -> {
            HapHapCircularProgressIndicator(
                modifier = modifier
                    .fillMaxSize()
                    .background(HapHapTheme.colors.white),
            )
        }
        LoginUiState.Idle, LoginUiState.Failure -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(HapHapTheme.colors.white)
                    .padding(start = 20.dp, end = 20.dp, bottom = 13.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable(onClick = onKakaoLoginClick)
                        .background(
                            color = HapHapTheme.colors.yellow,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(vertical = 13.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_login_kakao_logo),
                        contentDescription = null,
                        tint = HapHapTheme.colors.gray800,
                        modifier = Modifier.size(20.dp),
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "카카오로 시작하기",
                        style = HapHapTheme.typography.caption.sb12,
                        color = HapHapTheme.colors.gray800,
                    )
                }

                Spacer(modifier = Modifier.height(13.dp))
            }
        }
        LoginUiState.Success -> {
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    HapHapTheme {
        LoginScreen(
            uiState = LoginContract.State(),
            onKakaoLoginClick = {},
        )
    }
}