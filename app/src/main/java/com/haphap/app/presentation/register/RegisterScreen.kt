package com.haphap.app.presentation.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.presentation.register.type.PassResultStatusButton
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterRoute(
    entryPoint: RegisterSideEffect,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToJobDetail: (jobId: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navigateByEntryPoint: () -> Unit = {
        when (entryPoint) {
            RegisterSideEffect.Home -> navigateToHome()
            is RegisterSideEffect.JobDetail -> navigateToJobDetail(entryPoint.jobId)
        }
    }

    RegisterScreen(
        uiState = uiState,
        userName = "박연수",
        onAnnounceSelected = viewModel::onAnnounceSelected,
        onProcessSelected = viewModel::onProcessSelected,
        onStep1NextClick = viewModel::onStep1NextClick,
        onResultSelected = viewModel::onResultSelected,
        onChangeModalConfirmClick = viewModel::onChangeModalConfirmClick,
        onChangeModalCancelClick = viewModel::onChangeModalCancelClick,
        onStep2NextClick = viewModel::onStep2NextClick,
        onDateSelected = viewModel::onDateSelected,
        onTimeSelected = viewModel::onTimeSelected,
        onChannelToggled = viewModel::onChannelToggled,
        onStep3NextClick = viewModel::onStep3NextClick,
        onAlarmAgreeToggled = viewModel::onAlarmAgreeToggled,
        onTermAgreeToggled = viewModel::onTermAgreeToggled,
        onRegisterClick = viewModel::onRegisterClick,
        onBackClick = navigateBack,
        onCompleteBackClick = navigateByEntryPoint,
        onCompleteClick = {
            if (uiState.selectedResult == PassResultStatusButton.PASS) {
                viewModel.onPassShareEntryClick()
            } else {
                navigateByEntryPoint()
            }
        },
        onHomeClick = navigateByEntryPoint,
        modifier = modifier,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun RegisterScreen(
    uiState: RegisterContract.State,
    userName: String,
    onAnnounceSelected: (RegisterDropDownItemModel) -> Unit,
    onProcessSelected: (Int) -> Unit,
    onStep1NextClick: () -> Unit,
    onResultSelected: (PassResultStatusButton) -> Unit,
    onChangeModalConfirmClick: () -> Unit,
    onChangeModalCancelClick: () -> Unit,
    onStep2NextClick: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
    onChannelToggled: (NotificationChannelType) -> Unit,
    onStep3NextClick: () -> Unit,
    onAlarmAgreeToggled: (Boolean) -> Unit,
    onTermAgreeToggled: (Boolean) -> Unit,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
    onCompleteBackClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState.section) {
        RegisterSection.Input -> RegisterInputScreen(
            uiState = uiState,
            onAnnounceSelected = onAnnounceSelected,
            onProcessSelected = onProcessSelected,
            onStep1NextClick = onStep1NextClick,
            onResultSelected = onResultSelected,
            onChangeModalConfirmClick = onChangeModalConfirmClick,
            onChangeModalCancelClick = onChangeModalCancelClick,
            onStep2NextClick = onStep2NextClick,
            onDateSelected = onDateSelected,
            onTimeSelected = onTimeSelected,
            onChannelToggled = onChannelToggled,
            onStep3NextClick = onStep3NextClick,
            onBackClick = onBackClick,
            modifier = modifier,
        )

        RegisterSection.Result -> RegisterResultScreen(
            uiState = uiState,
            userName = userName,
            onAlarmAgreeToggled = onAlarmAgreeToggled,
            onTermAgreeToggled = onTermAgreeToggled,
            onRegisterClick = onRegisterClick,
            onCompleteClick = onCompleteClick,
            onHomeClick = onHomeClick,
            onBackClick = if (uiState.step == RegisterStep.CONFIRM) onBackClick else onCompleteBackClick,
            modifier = modifier,
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    HapHapTheme {
        RegisterScreen(
            uiState = RegisterContract.State(
                step = RegisterStep.ANNOUNCE_AND_PROCESS,
                announceList = persistentListOf(
                    RegisterDropDownItemModel(id = 1, text = "카카오 2026 신입 개발자 공개 채용"),
                    RegisterDropDownItemModel(id = 2, text = "네이버 2026 신입 개발자 공개 채용"),
                ),
            ),
            userName = "박연수",
            onAnnounceSelected = {},
            onProcessSelected = {},
            onStep1NextClick = {},
            onResultSelected = {},
            onChangeModalConfirmClick = {},
            onChangeModalCancelClick = {},
            onStep2NextClick = {},
            onDateSelected = {},
            onTimeSelected = {},
            onChannelToggled = {},
            onStep3NextClick = {},
            onAlarmAgreeToggled = {},
            onTermAgreeToggled = {},
            onRegisterClick = {},
            onBackClick = {},
            onCompleteBackClick = {},
            onCompleteClick = {},
            onHomeClick = {},
        )
    }
}
