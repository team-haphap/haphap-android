package com.haphap.app.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.core.designsystem.component.modal.HapHapDialog
import com.haphap.app.core.designsystem.component.toast.LocalToastTrigger
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.sendEmail
import com.haphap.app.presentation.setting.SettingContract.SideEffect.NavigateToLeave
import com.haphap.app.presentation.setting.SettingContract.SideEffect.NavigateToLogin
import com.haphap.app.presentation.setting.SettingContract.SideEffect.NavigateToMyPage
import com.haphap.app.presentation.setting.SettingContract.SideEffect.OnShowToast
import com.haphap.app.presentation.setting.component.SettingMenuItem
import com.haphap.app.presentation.setting.component.SettingSectionDivider
import com.haphap.app.presentation.setting.component.SettingSectionTitle
import com.haphap.app.presentation.setting.component.SettingTopBar

@Composable
fun SettingRoute(
    navigateBack: () -> Unit,
    navigateToAccountInfo: () -> Unit,
    navigateToLeave: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val showToast = LocalToastTrigger.current
    val context = LocalContext.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToMyPage -> navigateBack()
                    is NavigateToLeave -> navigateToLeave()
                    is NavigateToLogin -> navigateToLogin()
                    is OnShowToast -> showToast(sideEffect.message, sideEffect.isAlarm)
                }
            }
        }
    }

    SettingScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
        onInfoClick = navigateToAccountInfo,
        onInquiryClick = { context.sendEmail() },
        onLogoutClick = viewModel::onLogoutClick,
        onLogoutDialogDismiss = viewModel::onLogoutDialogDismiss,
        onLogoutConfirm = viewModel::onLogoutConfirm,
        onLeaveClick = viewModel::onLeaveClick,
        modifier = modifier,
    )
}

@Composable
private fun SettingScreen(
    uiState: SettingContract.State,
    onBackClick: () -> Unit,
    onInfoClick: () -> Unit,
    onInquiryClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onLogoutDialogDismiss: () -> Unit,
    onLogoutConfirm: () -> Unit,
    onLeaveClick: () -> Unit,
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
            .background(color = HapHapTheme.colors.white)
            .padding(horizontal = 20.dp),
    ) {
        SettingTopBar(
            onBackClick = onBackClick,
        )

        LazyColumn (
            contentPadding = PaddingValues(vertical = 16.dp),
        ){
            item {
                SettingSectionTitle(text = "계정")

                SettingMenuItem(
                    text = "계정 정보",
                    onClick = onInfoClick,
                )

                SettingSectionDivider()
            }

            item {
                SettingSectionTitle(text = "서비스 안내")

                SettingMenuItem(
                    text = "이용 약관",
                    onClick = {},
                )

                SettingMenuItem(
                    text = "개인정보 처리 방침",
                    onClick = {},
                )

                SettingMenuItem(
                    text = "커뮤니티 운영 정책",
                    onClick = {},
                )

                SettingMenuItem(
                    text = "문의하기",
                    onClick = onInquiryClick,
                )

                SettingSectionDivider()
            }

            item {
                SettingMenuItem(
                    text = "로그아웃",
                    textColor = HapHapTheme.colors.subred,
                    onClick = onLogoutClick,
                )

                SettingMenuItem(
                    text = "회원 탈퇴",
                    onClick = onLeaveClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    HapHapTheme {
        SettingScreen(
            uiState = SettingContract.State(),
            onBackClick = {},
            onInfoClick = {},
            onInquiryClick = {},
            onLogoutClick = {},
            onLogoutDialogDismiss = {},
            onLogoutConfirm = {},
            onLeaveClick = {},
        )
    }
}
