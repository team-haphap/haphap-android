package com.haphap.app.presentation.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.component.RegisterCompleteSection
import com.haphap.app.presentation.register.component.RegisterConfirmSection
import com.haphap.app.presentation.register.type.PassResultStatusButton
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterResultScreen(
    uiState: RegisterContract.State,
    onAlarmAgreeToggled: (Boolean) -> Unit,
    onTermAgreeToggled: (Boolean) -> Unit,
    onRegisterClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState.step) {
        RegisterStep.CONFIRM -> RegisterConfirmSection(
            recruitName = uiState.selectedAnnounce?.text.orEmpty(),
            recruitProcess = uiState.processList
                .find { it.id == uiState.selectedProcessId } ?.text.orEmpty(),
            contactDate = uiState.contactDate,
            contactTime = uiState.contactTime,
            selectedResult = uiState.selectedResult ?: PassResultStatusButton.DONT_KNOW,
            isAlarmAgreed = uiState.isAlarmAgreed,
            onAlarmAgreeToggled = onAlarmAgreeToggled,
            isTermAgreed = uiState.isTermsAgreed,
            onTermAgreeToggled = onTermAgreeToggled,
            isRegisterButtonEnabled = uiState.isRegisterButtonEnabled,
            onRegisterClick = onRegisterClick,
            onBackClick = onBackClick,
            modifier = modifier,
        )

        RegisterStep.COMPLETE -> RegisterCompleteSection(
            onBackClick = onBackClick,
            onCompleteClick = onCompleteClick,
            modifier = modifier,
        )

        else -> Unit
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterResultScreenPreview() {
    var uiState by remember {
        mutableStateOf(
            RegisterContract.State(
                step = RegisterStep.CONFIRM,
                selectedAnnounce = RegisterDropDownItemModel(
                    id = 1,
                    text = "카카오 2026 신입 개발자 공개 채용",
                ),
                processList = persistentListOf(RegisterProcessModel(id = 1, text = "코딩테스트")),
                selectedProcessId = 1,
                contactDate = LocalDate.of(2026, 6, 11),
                contactTime = LocalTime.of(21, 33),
                selectedResult = PassResultStatusButton.PASS,
                isAlarmAgreed = true,
                isTermsAgreed = true,
            )
        )
    }

    HapHapTheme {
        RegisterResultScreen(
            uiState = uiState,
            onAlarmAgreeToggled = { checked ->
                uiState = uiState.copy(isAlarmAgreed = checked)
            },
            onTermAgreeToggled = { checked ->
                uiState = uiState.copy(isTermsAgreed = checked)
            },
            onRegisterClick = {
                if (uiState.isRegisterButtonEnabled) {
                    uiState = uiState.copy(step = RegisterStep.COMPLETE)
                }
            },
            onCompleteClick = {},
            onBackClick = {},
        )
    }
}