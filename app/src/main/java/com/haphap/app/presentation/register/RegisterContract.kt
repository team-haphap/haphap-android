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
        val step: Int = 1,
        val isPassShareVariable: Boolean = false,
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
        val isButtonEnabled: Boolean = false,

        val passShareInfo: RegisterPassShareModel? = null,
    ) {
        val section: RegisterSection
            get() = if (step <= RegisterStep.THIRD) RegisterSection.Input else RegisterSection.Result

        fun toggleNotificationChannel(channel: NotificationChannelType): State =
            copy(
                selectedChannels = if (selectedChannels.contains(channel)) {
                    selectedChannels.remove(channel)
                } else {
                    selectedChannels.add(channel)
                }
            )

        fun refreshButtonEnabled(): State = copy(
            isButtonEnabled = when (step) {
                RegisterStep.FIRST -> selectedAnnounce != null && selectedProcessId != null
                RegisterStep.SECOND -> selectedResult != null
                RegisterStep.THIRD -> contactDate != null && contactTime != null
                RegisterStep.FOURTH -> isTermsAgreed && registerUiState !is RegisterUiState.Loading
                RegisterStep.FIFTH -> true
                else -> false
            }
        )
    }
}

object RegisterStep {
    const val FIRST = 1
    const val SECOND = 2
    const val THIRD = 3
    const val FOURTH = 4
    const val FIFTH = 5
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