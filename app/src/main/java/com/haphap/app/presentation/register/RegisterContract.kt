package com.haphap.app.presentation.register

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterPassShareModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.type.PassResultStatusButton
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalTime

sealed interface RegisterContract {
    @Immutable
    data class State(
        val step: RegisterStep = RegisterStep.ANNOUNCE_AND_PROCESS,
        val entryPoint: RegisterSideEffect = RegisterSideEffect.Home,

        val announceList: ImmutableList<RegisterDropDownItemModel> = persistentListOf(),
        val selectedAnnounce: RegisterDropDownItemModel? = null,
        val announceListUiState: RegisterUiState = RegisterUiState.Idle,
        val processList: ImmutableList<RegisterProcessModel> = persistentListOf(),
        val processListUiState: RegisterUiState = RegisterUiState.Idle,
        val selectedProcessId: Int? = null,

        val selectedResult: PassResultStatusButton? = null,
        val previousRegisteredResult: PassResultStatusButton? = null,
        val isChangeModalVisible: Boolean = false,

        val contactDate: LocalDate? = null,
        val contactTime: LocalTime? = null,
        val selectedChannels: PersistentList<NotificationChannelType> = persistentListOf(),

        val isAlarmAgreed: Boolean = false,
        val isTermsAgreed: Boolean = false,

        val registerUiState: RegisterUiState = RegisterUiState.Idle,

        val passShareInfo: RegisterPassShareModel? = null,
    ) {
        val section: RegisterSection
            get() = when (step) {
                RegisterStep.ANNOUNCE_AND_PROCESS,
                RegisterStep.RESULT,
                RegisterStep.DATE_AND_CHANNEL,
                    -> RegisterSection.Input

                RegisterStep.CONFIRM,
                RegisterStep.COMPLETE,
                RegisterStep.PASS_SHARE,
                    -> RegisterSection.Result
            }

        val isStep1NextEnabled: Boolean
            get() = selectedAnnounce != null && selectedProcessId != null

        val isStep2NextEnabled: Boolean
            get() = selectedResult != null

        val isStep3NextEnabled: Boolean
            get() = contactDate != null && contactTime != null

        val isRegisterButtonEnabled: Boolean
            get() = isTermsAgreed && registerUiState !is RegisterUiState.Loading

        fun toggleNotificationChannel(channel: NotificationChannelType): State =
            copy(
                selectedChannels = if (selectedChannels.contains(channel)) {
                    selectedChannels.remove(channel)
                } else {
                    selectedChannels.add(channel)
                }
            )
    }
}

enum class RegisterStep {
    ANNOUNCE_AND_PROCESS,
    RESULT,
    DATE_AND_CHANNEL,
    CONFIRM,
    COMPLETE,
    PASS_SHARE,
}

enum class NotificationChannelType(val text: String) {
    SMS("문자"),
    EMAIL("이메일"),
    CALL("전화"),
    WEB("기업 홈페이지"),
}

sealed interface RegisterSideEffect {
    data object Home: RegisterSideEffect
    data class JobDetail(val jobId: Long) : RegisterSideEffect
}

sealed interface RegisterSection {
    data object Input : RegisterSection
    data object Result : RegisterSection
}

sealed interface RegisterUiState {
    data object Idle : RegisterUiState
    data object Loading : RegisterUiState
    data object Empty : RegisterUiState
    data object Success : RegisterUiState
    data class Failure(val msg: String): RegisterUiState
}