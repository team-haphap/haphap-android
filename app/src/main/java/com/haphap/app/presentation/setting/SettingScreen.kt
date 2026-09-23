package com.haphap.app.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.setting.SettingContract.SideEffect.NavigateToMyPage
import com.haphap.app.presentation.setting.component.SettingTopBar

@Composable
fun SettingRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToMyPage -> navigateBack()
                }
            }
        }
    }

    SettingScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
        modifier = modifier,
    )
}

@Composable
private fun SettingScreen(
    uiState: SettingContract.State,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        SettingTopBar(
            onBackClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "계정",
            style = HapHapTheme.typography.caption.m12,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "계정 정보",
            modifier = Modifier.padding(vertical = 10.dp),
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
        )

        Spacer(modifier = Modifier.height(17.dp))

        HorizontalDivider(
            color = HapHapTheme.colors.gray200,
            thickness = 1.dp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "서비스 안내",
            style = HapHapTheme.typography.caption.m12,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "이용 약관",
            modifier = Modifier.padding(vertical = 10.dp),
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
        )

        Text(
            text = "개인정보 처리 방침",
            modifier = Modifier.padding(vertical = 10.dp),
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
        )

        Text(
            text = "커뮤니티 운영 정책",
            modifier = Modifier.padding(vertical = 10.dp),
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
        )

        Text(
            text = "문의하기",
            modifier = Modifier.padding(vertical = 10.dp),
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
        )

        Spacer(modifier = Modifier.height(17.dp))

        HorizontalDivider(
            color = HapHapTheme.colors.gray200,
            thickness = 1.dp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "로그아웃",
            modifier = Modifier.padding(vertical = 10.dp),
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.subred,
        )

        Text(
            text = "회원 탈퇴",
            modifier = Modifier.padding(vertical = 10.dp),
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
        )
    }
}
