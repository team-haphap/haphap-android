package com.haphap.app.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.haphap.app.core.designsystem.component.modal.HapHapDialog
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.setting.SettingContract.SideEffect.NavigateToMyPage
import com.haphap.app.presentation.setting.component.SettingMenuItem
import com.haphap.app.presentation.setting.component.SettingSectionDivider
import com.haphap.app.presentation.setting.component.SettingSectionTitle
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
        onLogoutClick = viewModel::onLogoutClick,
        onLogoutDialogDismiss = viewModel::onLogoutDialogDismiss,
        onLogoutConfirm = viewModel::onLogoutConfirm,
        modifier = modifier,
    )
}

@Composable
private fun SettingScreen(
    uiState: SettingContract.State,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onLogoutDialogDismiss: () -> Unit,
    onLogoutConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLogoutDialogVisible) {
        HapHapDialog(
            content = "로그아웃 하시겠습니까?",
            onDismiss = onLogoutDialogDismiss,
            onConfirmClick = onLogoutConfirm,
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        SettingTopBar(
            onBackClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingSectionTitle(text = "계정")

        Spacer(modifier = Modifier.height(12.dp))

        SettingMenuItem(text = "계정 정보")

        SettingSectionDivider()

        SettingSectionTitle(text = "서비스 안내")

        Spacer(modifier = Modifier.height(12.dp))

        SettingMenuItem(text = "이용 약관")
        SettingMenuItem(text = "개인정보 처리 방침")
        SettingMenuItem(text = "커뮤니티 운영 정책")
        SettingMenuItem(text = "문의하기")

        SettingSectionDivider()

        SettingMenuItem(
            text = "로그아웃",
            textColor = HapHapTheme.colors.subred,
            onClick = onLogoutClick,
        )
        SettingMenuItem(text = "회원 탈퇴")
    }
}
