package com.haphap.app.presentation.register

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegisterPassCardModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.data.model.register.RegistrationModel
import com.haphap.app.presentation.register.type.NotificationChannelType
import com.haphap.app.presentation.register.type.PassResultStatusButton
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

sealed interface RegisterContract {
    @Immutable
    data class State(
        val step: Int = 1,
        val registerInfo: RegisterModel = RegisterModel(),

        val announceList: ImmutableList<RegisterDropDownItemModel> = persistentListOf(),
        val selectedAnnounce: RegisterDropDownItemModel? = null,
        val announceListUiState: RegisterUiState = RegisterUiState.Idle,
        val processList: ImmutableList<RegisterProcessModel> = persistentListOf(),
        val processListUiState: RegisterUiState = RegisterUiState.Idle,

        val selectedResult: PassResultStatusButton? = null,
        val isChangeModalVisible: Boolean = false,

        val registerUiState: RegisterUiState = RegisterUiState.Idle,
        val registrationResult: RegistrationModel? = null,

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

    sealed class SideEffect {
        data class OnShowToast (
            val message: String,
            val isAlarm: Boolean = true,
        ): SideEffect()

        data object NavigateToHome : SideEffect()

        data class NavigateToJobDetail(
            val jobId: Int,
        ) : SideEffect()

        data class NavigateToPassCard(
            val passCard: RegisterPassCardModel,
        ) : SideEffect()
    }
}

sealed interface RegisterUiState {
    data object Idle : RegisterUiState
    data object Loading : RegisterUiState
    data object Empty : RegisterUiState
    data object Success : RegisterUiState
    data class Failure(val msg: String): RegisterUiState
}