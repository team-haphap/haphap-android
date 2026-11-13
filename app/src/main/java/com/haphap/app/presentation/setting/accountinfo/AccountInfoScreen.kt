package com.haphap.app.presentation.setting.accountinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.setting.accountinfo.component.AccountInfoItem
import com.haphap.app.presentation.setting.accountinfo.component.AccountInfoTopBar

@Composable
fun AccountInfoRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountInfoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AccountInfoScreen(
        uiState = uiState,
        onBackClick = navigateBack,
        modifier = modifier,
    )
}

@Composable
private fun AccountInfoScreen(
    uiState: AccountInfoContract.State,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = HapHapTheme.colors.white)
            .padding(horizontal = 20.dp),
    ) {
        AccountInfoTopBar(
            onBackClick = onBackClick,
        )

        AccountInfoItem(
            label = "로그인 수단",
            content = uiState.loginProvider,
            trailingImgRes = R.drawable.img_kakao,
        )

        AccountInfoItem(
            label = "이름",
            content = uiState.name,
        )

        AccountInfoItem(
            label = "이메일",
            content = uiState.email,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "소셜 계정에서 제공받은 정보로,\n합합에서는 수정할 수 없어요.",
            style = HapHapTheme.typography.body.sb13,
            color = HapHapTheme.colors.gray400,
        )
    }
}

@Preview
@Composable
private fun AccountInfoScreenPreview() {
    HapHapTheme {
        AccountInfoScreen(
            uiState = AccountInfoContract.State(
                loginProvider = "카카오",
                name = "박연수",
                email = "haphapsoptofficialyeonsoo@naver.com",
            ),
            onBackClick = {},
        )
    }
}
