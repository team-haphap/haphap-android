package com.haphap.app.presentation.register

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.component.toast.LocalToastTrigger
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.core.extensions.toastBottomInset
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterPassCardModel
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateBack
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateToHome
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateToJobDetail
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateToPassCard
import com.haphap.app.presentation.register.RegisterContract.SideEffect.OnShowToast
import com.haphap.app.presentation.register.component.RegisterFifthSection
import com.haphap.app.presentation.register.component.RegisterFirstSection
import com.haphap.app.presentation.register.component.RegisterFourthSection
import com.haphap.app.presentation.register.component.RegisterPassProofSection
import com.haphap.app.presentation.register.component.RegisterProgressBar
import com.haphap.app.presentation.register.component.RegisterSecondSection
import com.haphap.app.presentation.register.component.RegisterThirdSection
import com.haphap.app.presentation.register.component.RegisterTopBar
import com.haphap.app.presentation.register.type.NotificationChannelType
import com.haphap.app.presentation.register.type.PassResultStatusButton
import com.haphap.app.presentation.register.type.RegisterStep
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun RegisterRoute(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToJobDetail: (jobId: Int) -> Unit,
    navigateToPassCard: (passCard: RegisterPassCardModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val remainingCount = uiState.remainingProofImageCount

    val singleImagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        uri?.let { viewModel.onImagesPicked(listOf(it)) }
    }

    val multipleImagePickerContract = remember(remainingCount) {
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = remainingCount.coerceAtLeast(2))
    }
    val multipleImagePicker = rememberLauncherForActivityResult(
        multipleImagePickerContract
    ) { uris ->
        viewModel.onImagesPicked(uris)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val showToast = LocalToastTrigger.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is OnShowToast -> showToast.invoke(sideEffect.message, sideEffect.isAlarm)
                    is NavigateBack -> navigateBack()
                    is NavigateToHome -> navigateToHome()
                    is NavigateToJobDetail -> navigateToJobDetail(sideEffect.jobId)
                    is NavigateToPassCard -> navigateToPassCard(sideEffect.passCard)
                }
            }
        }
    }

    BackHandler(enabled = uiState.step != RegisterStep.COMPLETE) { viewModel.onBackClick() }

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
        onAddImageClick = {
            val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

            when {
                remainingCount <= 0 -> viewModel.onImageLimitExceeded()
                remainingCount == 1 -> singleImagePicker.launch(request)
                else -> multipleImagePicker.launch(request)
            }
        },
        onRemoveImage = viewModel::onRemoveImage,
        onAlarmAgreeToggled = viewModel::onAlarmAgreeToggled,
        onTermAgreeToggled = viewModel::onTermAgreeToggled,
        onNextClick = viewModel::onNextClick,
        onBackClick = viewModel::onBackClick,
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
    onAddImageClick: () -> Unit,
    onRemoveImage: (Uri) -> Unit,
    onAlarmAgreeToggled: (Boolean) -> Unit,
    onTermAgreeToggled: (Boolean) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = HapHapTheme.colors.white,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            if (uiState.step.isTopBarVisible) {
                Column {
                    RegisterTopBar(
                        onBackClick = onBackClick,
                        isText = true,
                    )

                    if (uiState.step.isProgressBarVisible) {
                        RegisterProgressBar(
                            progress = uiState.step.progress,
                            totalSteps = RegisterStep.progressStepCount,
                        )
                    } else {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        },
        bottomBar = {
            HapHapBasicButton(
                text = uiState.step.btnText,
                textStyle = HapHapTheme.typography.body.b18,
                colorType = ButtonType.Primary(enabled = uiState.isButtonEnabled),
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .toastBottomInset(),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState.step) {
                RegisterStep.SELECT_PROCESS -> RegisterFirstSection(
                    announceList = uiState.announceList,
                    selectedAnnounce = uiState.selectedAnnounce,
                    onAnnounceSelected = onAnnounceSelected,
                    processList = uiState.processList,
                    isProcessListSuccess = uiState.processListUiState == RegisterUiState.Success,
                    selectedProcessId = uiState.registerInfo.stageId,
                    onProcessSelected = onProcessSelected,
                    modifier = Modifier.fillMaxSize(),
                )

                RegisterStep.RESULT -> RegisterSecondSection(
                    selectedResult = uiState.selectedResult,
                    onResultSelected = onResultSelected,
                    isChangeModalVisible = uiState.isChangeModalVisible,
                    onChangeModalConfirmClick = onChangeModalConfirmClick,
                    onChangeModalCancelClick = onChangeModalCancelClick,
                    modifier = Modifier.fillMaxSize(),
                )

                RegisterStep.CONTACT -> RegisterThirdSection(
                    contactDate = uiState.registerInfo.contactedDate,
                    onDateSelected = onDateSelected,
                    contactTime = uiState.registerInfo.contactedTime,
                    onTimeSelected = onTimeSelected,
                    selectedChannels = uiState.registerInfo.contactedMethod,
                    onChannelToggled = onChannelToggled,
                    modifier = Modifier.fillMaxSize(),
                )

                RegisterStep.PASS_PROOF -> RegisterPassProofSection(
                    images = uiState.proofImages,
                    onAddImageClick = onAddImageClick,
                    onRemoveImage = onRemoveImage,
                    modifier = Modifier.fillMaxSize(),
                )

                RegisterStep.CONFIRM -> RegisterFourthSection(
                    recruitName = uiState.selectedAnnounce?.text.orEmpty(),
                    recruitProcess = uiState.processList.find {
                        it.id == uiState.registerInfo.stageId
                    }?.text.orEmpty(),
                    contactDate = uiState.registerInfo.contactedDate,
                    contactTime = uiState.registerInfo.contactedTime,
                    selectedResult = uiState.selectedResult ?: PassResultStatusButton.DONT_KNOW,
                    isAlarmAgreed = uiState.registerInfo.alarmEnabled,
                    onAlarmAgreeToggled = onAlarmAgreeToggled,
                    isTermAgreed = uiState.registerInfo.anonymous,
                    onTermAgreeToggled = onTermAgreeToggled,
                    proofImages = uiState.validProofImageUris,
                    modifier = Modifier.fillMaxSize(),
                )

                RegisterStep.COMPLETE -> RegisterFifthSection(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
