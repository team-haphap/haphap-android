package com.haphap.app.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.component.toast.LocalToastTrigger
import com.haphap.app.presentation.register.RegisterContract.SideEffect.OnShowToast
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterPassCardModel
import com.haphap.app.presentation.register.component.RegisterFifthSection
import com.haphap.app.presentation.register.component.RegisterFirstSection
import com.haphap.app.presentation.register.component.RegisterFourthSection
import com.haphap.app.presentation.register.component.RegisterProgressBar
import com.haphap.app.presentation.register.component.RegisterSecondSection
import com.haphap.app.presentation.register.component.RegisterThirdSection
import com.haphap.app.presentation.register.component.RegisterTopBar
import com.haphap.app.presentation.register.type.NotificationChannelType
import com.haphap.app.presentation.register.type.PassResultStatusButton
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun RegisterRoute(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToJobDetail: (jobId: Long) -> Unit,
    navigateToPassCard: (passCard: RegisterPassCardModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val showToast = LocalToastTrigger.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is OnShowToast -> showToast.invoke(sideEffect.message, sideEffect.isAlarm)
                }
            }
        }
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
                1 -> viewModel.onStep1NextClick()
                2 -> viewModel.onStep2NextClick()
                3 -> viewModel.onStep3NextClick()
                4 -> viewModel.onRegisterClick()
                5 -> {
                    if (uiState.selectedResult == PassResultStatusButton.PASS) {
                        uiState.registrationResult?.card?.let { navigateToPassCard(it) }
                    } else {
                        when (val entryPoint = uiState.entryPoint) {
                            RegisterContract.RegisterSideEffect.Home -> navigateToHome()
                            is RegisterContract.RegisterSideEffect.JobDetail -> navigateToJobDetail(
                                entryPoint.jobId
                            )
                        }
                    }
                }
            }
        },
        onBackClick = {
            if (uiState.step == 1) {
                navigateBack()
            } else {
                viewModel.onBackClick()
            }
        },
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
    Scaffold(
        modifier = modifier,
        containerColor = HapHapTheme.colors.white,
        topBar = {
            if (uiState.step < 5) {
                Column {
                    RegisterTopBar(
                        onBackClick = onBackClick,
                        isText = true,
                    )

                    if (uiState.step < 3) {
                        RegisterProgressBar(
                            progress = uiState.step,
                            totalSteps = 3,
                        )
                    } else if (uiState.step == 4) {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        },
        bottomBar = {
            HapHapBasicButton(
                text = when (uiState.step) {
                    1, 2, 3 -> "다음"
                    4 -> "등록하기"
                    else -> "완료"
                },
                textStyle = HapHapTheme.typography.body.b18,
                colorType = ButtonType.Primary(enabled = uiState.isButtonEnabled),
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
                1 -> RegisterFirstSection(
                    announceList = uiState.announceList,
                    selectedAnnounce = uiState.selectedAnnounce,
                    onAnnounceSelected = onAnnounceSelected,
                    processList = uiState.processList,
                    isProcessListSuccess = uiState.processListUiState == RegisterUiState.Success,
                    selectedProcessId = uiState.registerInfo.stageId,
                    onProcessSelected = onProcessSelected,
                    modifier = Modifier.fillMaxSize(),
                )

                2 -> RegisterSecondSection(
                    selectedResult = uiState.selectedResult,
                    onResultSelected = onResultSelected,
                    isChangeModalVisible = uiState.isChangeModalVisible,
                    onChangeModalConfirmClick = onChangeModalConfirmClick,
                    onChangeModalCancelClick = onChangeModalCancelClick,
                    modifier = Modifier.fillMaxSize(),
                )

                3 -> RegisterThirdSection(
                    contactDate = uiState.registerInfo.contactedDate,
                    onDateSelected = onDateSelected,
                    contactTime = uiState.registerInfo.contactedTime,
                    onTimeSelected = onTimeSelected,
                    selectedChannels = uiState.registerInfo.contactedMethod,
                    onChannelToggled = onChannelToggled,
                    modifier = Modifier.fillMaxSize(),
                )

                4 -> RegisterFourthSection(
                    recruitName = uiState.selectedAnnounce?.text.orEmpty(),
                    recruitProcess = uiState.processList.find { it.id == uiState.registerInfo.stageId }?.text.orEmpty(),
                    contactDate = uiState.registerInfo.contactedDate,
                    contactTime = uiState.registerInfo.contactedTime,
                    selectedResult = uiState.selectedResult ?: PassResultStatusButton.DONT_KNOW,
                    isAlarmAgreed = uiState.registerInfo.alarmEnabled,
                    onAlarmAgreeToggled = onAlarmAgreeToggled,
                    isTermAgreed = uiState.registerInfo.anonymous,
                    onTermAgreeToggled = onTermAgreeToggled,
                    modifier = Modifier.fillMaxSize()
                )

                5 -> RegisterFifthSection(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
