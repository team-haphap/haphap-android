package com.haphap.app.presentation.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.mypage.component.MyPageProfileCard
import com.haphap.app.presentation.mypage.component.MyPageTopSection

@Composable
fun MyPageRoute(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    MyPageScreen(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun MyPageScreen(
    uiState: MyPageUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 43.dp),
    ) {
        MyPageTopSection(
            nickNameText = uiState.nickNameText,
        )

        Spacer(modifier = Modifier.height(42.dp))

        MyPageProfileCard(
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
            uiState = MyPageUiState(
                nickNameText = "익명의죠르디",
                emailText = "yeonsoo1234@naver.com",
            ),
        )
    }
}
