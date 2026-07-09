package com.haphap.app.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.component.RegisterAnnounceProcessSection
import com.haphap.app.presentation.register.component.RegisterDateChannelSection
import com.haphap.app.presentation.register.component.RegisterProgressBar
import com.haphap.app.presentation.register.component.RegisterResultSection
import com.haphap.app.presentation.register.component.RegisterTopBar
import com.haphap.app.presentation.register.type.PassResultStatusButton
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
fun RegisterInputScreen(
    uiState: RegisterContract.State,
    onAnnounceSelected: (RegisterDropDownItemModel) -> Unit,
    onProcessSelected: (Int) -> Unit,
    onStep1NextClick: () -> Unit,
    onResultSelected: (PassResultStatusButton) -> Unit,
    onChangeModalConfirmClick: () -> Unit,
    onChangeModalCancelClick: () -> Unit,
    onStep2NextClick: () -> Unit,
    onDateSelected: (java.time.LocalDate) -> Unit,
    onTimeSelected: (java.time.LocalTime) -> Unit,
    onChannelToggled: (NotificationChannelType) -> Unit,
    onStep3NextClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val progress = when (uiState.step) {
        RegisterStep.ANNOUNCE_AND_PROCESS -> 1
        RegisterStep.RESULT -> 2
        RegisterStep.DATE_AND_CHANNEL -> 3
        else -> 1
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white)
    ) {
        RegisterTopBar(onBackClick = onBackClick)

        RegisterProgressBar(progress = progress, totalSteps = 3)

        when (uiState.step) {
            RegisterStep.ANNOUNCE_AND_PROCESS -> RegisterAnnounceProcessSection(
                announceList = uiState.announceList,
                selectedAnnounce = uiState.selectedAnnounce,
                onAnnounceSelected = onAnnounceSelected,
                processList = uiState.processList,
                processListUiState = uiState.processListUiState,
                selectedProcessId = uiState.selectedProcessId,
                onProcessSelected = onProcessSelected,
                isNextEnabled = uiState.isStep1NextEnabled,
                onNextClick = onStep1NextClick,
            )

            RegisterStep.RESULT -> RegisterResultSection(
                selectedResult = uiState.selectedResult,
                onResultSelected = onResultSelected,
                isChangeModalVisible = uiState.isChangeModalVisible,
                onChangeModalConfirmClick = onChangeModalConfirmClick,
                onChangeModalCancelClick = onChangeModalCancelClick,
                isNextEnabled = uiState.isStep2NextEnabled,
                onNextClick = onStep2NextClick,
            )

            RegisterStep.DATE_AND_CHANNEL -> RegisterDateChannelSection(
                contactDate = uiState.contactDate,
                onDateSelected = onDateSelected,
                contactTime = uiState.contactTime,
                onTimeSelected = onTimeSelected,
                selectedChannels = uiState.selectedChannels,
                onChannelToggled = onChannelToggled,
                isNextEnabled = uiState.isStep3NextEnabled,
                onNextClick = onStep3NextClick,
            )

            RegisterStep.CONFIRM, RegisterStep.COMPLETE, RegisterStep.PASS_SHARE -> Unit
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterInputScreenPreview() {
    val processListByAnnounceId = remember {
        mapOf(
            1 to persistentListOf(
                RegisterProcessModel(id = 1, text = "서류"),
                RegisterProcessModel(id = 2, text = "AI 역량 검사"),
                RegisterProcessModel(id = 3, text = "1차면접"),
                RegisterProcessModel(id = 4, text = "2차면접"),
                RegisterProcessModel(id = 5, text = "임원면접"),
                RegisterProcessModel(id = 6, text = "최종"),
            ),
            2 to persistentListOf(
                RegisterProcessModel(id = 7, text = "서류"),
                RegisterProcessModel(id = 8, text = "코딩테스트"),
                RegisterProcessModel(id = 9, text = "면접"),
            ),
        )
    }

    val previousResults = remember {
        mapOf("1-1" to PassResultStatusButton.FAILED)
    }

    var uiState by remember {
        mutableStateOf(
            RegisterContract.State(
                announceList = persistentListOf(
                    RegisterDropDownItemModel(id = 1, text = "카카오 2026 신입 개발자 공개 채용"),
                    RegisterDropDownItemModel(id = 2, text = "네이버 2026 신입 개발자 공개 채용"),
                ),
            )
        )
    }
    val coroutineScope = rememberCoroutineScope()

    HapHapTheme {
        RegisterInputScreen(
            uiState = uiState,
            onAnnounceSelected = { item ->
                uiState = uiState.copy(
                    selectedAnnounce = item,
                    selectedProcessId = null,
                    processList = persistentListOf(),
                    processListUiState = RegisterUiState.Loading,
                )
                coroutineScope.launch {
                    uiState = uiState.copy(
                        processList = processListByAnnounceId[item.id] ?: persistentListOf(),
                        processListUiState = RegisterUiState.Success,
                    )
                }
            },
            onProcessSelected = { id ->
                val announceId = uiState.selectedAnnounce?.id
                val previousResult = previousResults["$announceId-$id"]
                uiState = uiState.copy(
                    selectedProcessId = id,
                    previousRegisteredResult = previousResult,
                )
            },
            onStep1NextClick = {
                if (uiState.isStep1NextEnabled) {
                    uiState = uiState.copy(step = RegisterStep.RESULT)
                }
            },
            onResultSelected = { result ->
                val previous = uiState.previousRegisteredResult
                uiState = when {
                    previous == result -> uiState
                    previous != null && previous != result -> uiState.copy(
                        selectedResult = result,
                        isChangeModalVisible = true,
                    )
                    else -> uiState.copy(selectedResult = result)
                }
            },
            onChangeModalConfirmClick = {
                uiState = uiState.copy(isChangeModalVisible = false)
            },
            onChangeModalCancelClick = {
                uiState = uiState.copy(
                    isChangeModalVisible = false,
                    step = RegisterStep.ANNOUNCE_AND_PROCESS,
                    selectedResult = null,
                )
            },
            onStep2NextClick = {
                val result = uiState.selectedResult
                if (result != null) {
                    uiState = uiState.copy(
                        step = if (result == PassResultStatusButton.DONT_KNOW) {
                            RegisterStep.CONFIRM
                        } else {
                            RegisterStep.DATE_AND_CHANNEL
                        },
                    )
                }
            },
            onDateSelected = { uiState = uiState.copy(contactDate = it) },
            onTimeSelected = { uiState = uiState.copy(contactTime = it) },
            onChannelToggled = { uiState = uiState.toggleNotificationChannel(it) },
            onStep3NextClick = {
                if (uiState.isStep3NextEnabled) {
                    uiState = uiState.copy(step = RegisterStep.CONFIRM)
                }
            },
            onBackClick = {},
        )
    }
}