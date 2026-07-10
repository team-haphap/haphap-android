package com.haphap.app.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.presentation.register.component.RegisterAnnounceProcessSection
import com.haphap.app.presentation.register.component.RegisterCompleteSection
import com.haphap.app.presentation.register.component.RegisterConfirmSection
import com.haphap.app.presentation.register.component.RegisterDateChannelSection
import com.haphap.app.presentation.register.component.RegisterPassCardSection
import com.haphap.app.presentation.register.component.RegisterProgressBar
import com.haphap.app.presentation.register.component.RegisterResultSection
import com.haphap.app.presentation.register.component.RegisterTopBar
import com.haphap.app.presentation.register.type.PassResultStatusButton
import com.haphap.app.data.model.register.RegisterPassShareModel
import java.time.LocalDate
import java.time.LocalTime
import kotlin.collections.find

@Composable
fun RegisterRoute(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToJobDetail: (jobId: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.step == RegisterStep.PASS_SHARE) {
        val passShareModel = uiState.passShareInfo ?: RegisterPassShareModel(
            companyName = "",
            logoUrl = "",
            backgroundImageUrl = "",
        )

        RegisterPassCardSection(
            userName = "userName",
            recruitName = uiState.selectedAnnounce?.text.orEmpty(),
            passShareModel = passShareModel,
            onHomeClick = navigateToHome,
            modifier = modifier
                .fillMaxSize()
                .background(HapHapTheme.colors.white),
        )
        return
    }

    RegisterScreen(
        uiState = uiState,
        onAnnounceSelected = viewModel::onAnnounceSelected,
        onProcessSelected = viewModel::onProcessSelected,
        onResultSelected = viewModel::onResultSelected,
        onChangeModalConfirmClick = viewModel::onChangeModalConfirmClick,
        onChangeModalCancelClick = viewModel::onChangeModalCancelClick,
        onDateSelected = viewModel::onDateSelected,
        onTimeSelected = viewModel::onTimeSelected,
        onChannelToggled = viewModel::onChannelToggled,
        onAlarmAgreeToggled = viewModel::onAlarmAgreeToggled,
        onTermAgreeToggled = viewModel::onTermAgreeToggled,
        onNextClick = {
            when (uiState.step) {
                RegisterStep.ANNOUNCE_AND_PROCESS -> viewModel.onStep1NextClick()
                RegisterStep.RESULT -> viewModel.onStep2NextClick()
                RegisterStep.DATE_AND_CHANNEL -> viewModel.onStep3NextClick()
                RegisterStep.CONFIRM -> viewModel.onRegisterClick()
                RegisterStep.COMPLETE -> {
                    if (uiState.selectedResult == PassResultStatusButton.PASS) {
                        viewModel.onPassShareEntryClick()
                    } else {
                        when (val entryPoint = uiState.entryPoint) {
                            RegisterSideEffect.Home -> navigateToHome()
                            is RegisterSideEffect.JobDetail -> navigateToJobDetail(entryPoint.jobId)
                        }
                    }
                }
                RegisterStep.PASS_SHARE -> Unit
            }
        },
        onBackClick = navigateBack,
        modifier = modifier,
    )
}

@Composable
private fun RegisterScreen(
    uiState: RegisterContract.State,
    onAnnounceSelected: (RegisterDropDownItemModel) -> Unit,
    onProcessSelected: (Int) -> Unit,
    onResultSelected: (PassResultStatusButton) -> Unit,
    onChangeModalConfirmClick: () -> Unit,
    onChangeModalCancelClick: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
    onChannelToggled: (NotificationChannelType) -> Unit,
    onAlarmAgreeToggled: (Boolean) -> Unit,
    onTermAgreeToggled: (Boolean) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val progress = when (uiState.step) {
        RegisterStep.ANNOUNCE_AND_PROCESS -> 1
        RegisterStep.RESULT -> 2
        RegisterStep.DATE_AND_CHANNEL -> 3
        RegisterStep.CONFIRM -> 4
        RegisterStep.COMPLETE -> 5
        RegisterStep.PASS_SHARE -> 5
    }

    val isNextEnabled = when (uiState.step) {
        RegisterStep.ANNOUNCE_AND_PROCESS -> uiState.isStep1NextEnabled
        RegisterStep.RESULT -> uiState.isStep2NextEnabled
        RegisterStep.DATE_AND_CHANNEL -> uiState.isStep3NextEnabled
        RegisterStep.CONFIRM -> uiState.isRegisterButtonEnabled
        RegisterStep.COMPLETE, RegisterStep.PASS_SHARE -> true
    }

    Scaffold(
        modifier = modifier,
        containerColor = HapHapTheme.colors.white,
        topBar = {
            if (progress < 5) {
                Column {
                    RegisterTopBar(
                        onBackClick = onBackClick,
                        isText = true,
                    )

                    if (progress <= 3) {
                        RegisterProgressBar(
                            progress = progress,
                            totalSteps = 3,
                        )
                    } else if (progress == 4) {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        },
        bottomBar = {
            HapHapBasicButton(
                text = when (uiState.step) {
                    RegisterStep.ANNOUNCE_AND_PROCESS,
                    RegisterStep.RESULT,
                    RegisterStep.DATE_AND_CHANNEL -> "다음"

                    RegisterStep.CONFIRM -> "등록하기"
                    else -> "완료"
                },
                textStyle = HapHapTheme.typography.body.b18,
                colorType = ButtonType.Primary(enabled = isNextEnabled),
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState.step) {
                RegisterStep.ANNOUNCE_AND_PROCESS -> RegisterAnnounceProcessSection(
                    announceList = uiState.announceList,
                    selectedAnnounce = uiState.selectedAnnounce,
                    onAnnounceSelected = onAnnounceSelected,
                    processList = uiState.processList,
                    processListUiState = uiState.processListUiState,
                    selectedProcessId = uiState.selectedProcessId,
                    onProcessSelected = onProcessSelected,
                    modifier = Modifier.fillMaxSize(),
                )

                RegisterStep.RESULT -> RegisterResultSection(
                    selectedResult = uiState.selectedResult,
                    onResultSelected = onResultSelected,
                    isChangeModalVisible = uiState.isChangeModalVisible,
                    onChangeModalConfirmClick = onChangeModalConfirmClick,
                    onChangeModalCancelClick = onChangeModalCancelClick,
                    modifier = Modifier.fillMaxSize(),
                )

                RegisterStep.DATE_AND_CHANNEL -> RegisterDateChannelSection(
                    contactDate = uiState.contactDate,
                    onDateSelected = onDateSelected,
                    contactTime = uiState.contactTime,
                    onTimeSelected = onTimeSelected,
                    selectedChannels = uiState.selectedChannels,
                    onChannelToggled = onChannelToggled,
                    modifier = Modifier.fillMaxSize(),
                )

                RegisterStep.CONFIRM -> RegisterConfirmSection(
                    recruitName = uiState.selectedAnnounce?.text.orEmpty(),
                    recruitProcess = uiState.processList.find { it.id == uiState.selectedProcessId }?.text.orEmpty(),
                    contactDate = uiState.contactDate,
                    contactTime = uiState.contactTime,
                    selectedResult = uiState.selectedResult ?: PassResultStatusButton.DONT_KNOW,
                    isAlarmAgreed = uiState.isAlarmAgreed,
                    onAlarmAgreeToggled = onAlarmAgreeToggled,
                    isTermAgreed = uiState.isTermsAgreed,
                    onTermAgreeToggled = onTermAgreeToggled,
                    modifier = Modifier.fillMaxSize()
                )

                RegisterStep.COMPLETE -> RegisterCompleteSection(
                    modifier = Modifier.fillMaxSize(),
                )

                RegisterStep.PASS_SHARE -> Unit
            }
        }
    }
}
