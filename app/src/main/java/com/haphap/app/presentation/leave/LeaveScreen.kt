package com.haphap.app.presentation.leave

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
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
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.presentation.leave.LeaveContract.SideEffect.NavigateToLeaveComplete
import com.haphap.app.presentation.leave.LeaveContract.SideEffect.NavigateToSetting
import com.haphap.app.presentation.leave.component.LeaveCheckItem
import com.haphap.app.presentation.leave.component.LeaveTopBar
import com.haphap.app.presentation.leave.type.LeaveReasonType

@Composable
fun LeaveRoute(
    navigateBack: () -> Unit,
    navigateToLeaveComplete: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LeaveViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToSetting -> navigateBack()
                    is NavigateToLeaveComplete -> navigateToLeaveComplete()
                }
            }
        }
    }

    LeaveScreen(
        uiState = uiState,
        etcReasonState = viewModel.etcReasonState,
        onBackClick = viewModel::onBackClick,
        onReasonClick = viewModel::onReasonClick,
        onLeaveClick = viewModel::onLeaveClick,
        modifier = modifier,
    )
}

@Composable
private fun LeaveScreen(
    uiState: LeaveContract.State,
    etcReasonState: TextFieldState,
    onBackClick: () -> Unit,
    onReasonClick: (LeaveReasonType) -> Unit,
    onLeaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        LeaveTopBar(
            onBackClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "탈퇴 유의사항",
                style = HapHapTheme.typography.caption.m12,
                color = HapHapTheme.colors.gray400,
            )

            Spacer(modifier = Modifier.height(24.dp))

            // TODO: 탈퇴 유의 사항 기입

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(
                color = HapHapTheme.colors.gray200,
                thickness = 1.dp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "탈퇴 사유를 선택해주세요(필수)",
                style = HapHapTheme.typography.body.sb14,
                color = HapHapTheme.colors.gray700,
            )

            Spacer(modifier = Modifier.height(12.dp))

            LeaveReasonType.entries.forEach { reason ->
                LeaveCheckItem(
                    context = reason.text,
                    onCheckedChange = { onReasonClick(reason) },
                    checked = reason == uiState.selectedReason,
                    isEtc = reason == LeaveReasonType.ETC,
                    etcReasonState = etcReasonState,
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                HapHapBasicButton(
                    text = "취소",
                    onClick = onBackClick,
                    modifier = Modifier.weight(1f),
                    textStyle = HapHapTheme.typography.body.b18,
                    colorType = ButtonType.Cancel,
                )
                HapHapBasicButton(
                    text = "탈퇴하기",
                    onClick = onLeaveClick,
                    modifier = Modifier.weight(1f),
                    textStyle = HapHapTheme.typography.body.b18,
                    colorType = ButtonType.Primary(enabled = uiState.isButtonEnabled),
                )
            }
        }
    }
}