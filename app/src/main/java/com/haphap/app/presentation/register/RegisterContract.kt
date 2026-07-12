package com.haphap.app.presentation.register

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.type.NotificationChannelType
import com.haphap.app.presentation.register.type.PassResultStatusButton
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

sealed interface RegisterContract {
    @Immutable
    data class State(
        val step: Int = 1,
        val entryPoint: RegisterSideEffect = RegisterSideEffect.Home,
        val registerInfo: RegisterModel = RegisterModel(),

        val announceList: ImmutableList<RegisterDropDownItemModel> = persistentListOf(),
        val selectedAnnounce: RegisterDropDownItemModel? = null,
        val announceListUiState: RegisterUiState = RegisterUiState.Idle,
        val processList: ImmutableList<RegisterProcessModel> = persistentListOf(),
        val processListUiState: RegisterUiState = RegisterUiState.Idle,

        val selectedResult: PassResultStatusButton? = null,
//        val previousRegisteredResult: PassResultStatusButton? = null,
        val isChangeModalVisible: Boolean = false,

        val registerUiState: RegisterUiState = RegisterUiState.Idle,
        val isButtonEnabled: Boolean = false,
    ) {
        fun toggleNotificationChannel(channel: NotificationChannelType): State =
            copy(
                registerInfo = registerInfo.copy(
                    contactedMethod = if (registerInfo.contactedMethod.contains(channel)) {
                        (registerInfo.contactedMethod - channel).toImmutableList()
                    } else {
                        (registerInfo.contactedMethod + channel).toImmutableList()
                    }
                )
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