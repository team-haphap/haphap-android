package com.haphap.app.presentation.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.mypage.MyPageContract.SideEffect.NavigateToHome
import com.haphap.app.presentation.mypage.component.MyPageProfileCard
import com.haphap.app.presentation.mypage.component.MyPageTopBar
import com.haphap.app.presentation.mypage.component.MyPageTopSection

@Composable
fun MyPageRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToHome -> navigateToHome()
                }
            }
        }
    }

    MyPageScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
        modifier = modifier,
    )
}

@Composable
private fun MyPageScreen(
    uiState: MyPageContract.State,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        MyPageTopBar(
            onBackClick = onBackClick,
            onSettingClick = {},
        )

        Spacer(modifier = Modifier.height(20.dp))

        MyPageTopSection(
            nameText = uiState.nameText,
        )

        Spacer(modifier = Modifier.height(42.dp))

        MyPageProfileCard(
            profileImage = uiState.profileUrl,
            nickNameText = uiState.nickNameText,
            emailText = uiState.emailText,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    HapHapTheme {
        MyPageScreen(
            uiState = MyPageContract.State(
                nickNameText = "익명의죠르디",
                emailText = "yeonsoo1234@naver.com",
            ),
            onBackClick = {},
        )
    }
}
