package com.haphap.app.presentation.register

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.type.NotificationChannelType
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
    ) {
        fun toggleNotificationChannel(channel: NotificationChannelType): State =
            copy(
                selectedChannels = if (selectedChannels.contains(channel)) {
                    selectedChannels.remove(channel)
                } else {
                    selectedChannels.add(channel)
                }
            )
    }

    sealed interface RegisterSideEffect {
        data object Home : RegisterSideEffect
        data class JobDetail(val jobId: Long) : RegisterSideEffect
    }
}

sealed interface RegisterUiState {
    data object Idle : RegisterUiState
    data object Loading : RegisterUiState
    data object Empty : RegisterUiState
    data object Success : RegisterUiState
    data class Failure(val msg: String): RegisterUiState
}