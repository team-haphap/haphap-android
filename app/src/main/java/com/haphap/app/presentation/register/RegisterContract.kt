package com.haphap.app.presentation.register

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.register.NotificationChannelType
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegisterPassCardModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.data.model.register.RegistrationModel
import com.haphap.app.presentation.register.model.MAX_PROOF_IMAGE_COUNT
import com.haphap.app.presentation.register.model.RegisterProofImageModel
import com.haphap.app.presentation.register.type.PassResultStatusButton
import com.haphap.app.presentation.register.type.RegisterStep
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

sealed interface RegisterContract {
    @Immutable
    data class State(
        val step: RegisterStep = RegisterStep.SELECT_PROCESS,
        val registerInfo: RegisterModel = RegisterModel(),

        val announceList: ImmutableList<RegisterDropDownItemModel> = persistentListOf(),
        val selectedAnnounce: RegisterDropDownItemModel? = null,
        val announceListUiState: RegisterUiState = RegisterUiState.Idle,
        val processList: ImmutableList<RegisterProcessModel> = persistentListOf(),
        val processListUiState: RegisterUiState = RegisterUiState.Idle,

        val selectedResult: PassResultStatusButton? = null,
        val isChangeModalVisible: Boolean = false,

        val proofImages: ImmutableList<RegisterProofImageModel> = persistentListOf(),

        val registerUiState: RegisterUiState = RegisterUiState.Idle,
        val registrationResult: RegistrationModel? = null,
    ) {
        val isButtonEnabled: Boolean
            get() = when (step) {
                RegisterStep.SELECT_PROCESS ->
                    selectedAnnounce != null && registerInfo.stageId != null

                RegisterStep.RESULT -> selectedResult != null

                RegisterStep.CONTACT ->
                    isContactDateTimeValid(registerInfo.contactedDate, registerInfo.contactedTime) &&
                            registerInfo.contactedMethod.isNotEmpty()

                RegisterStep.PASS_PROOF -> proofImages.any { it.error == null }

                RegisterStep.CONFIRM ->
                    registerInfo.anonymous && registerUiState !is RegisterUiState.Loading

                RegisterStep.COMPLETE -> true
            }

        val remainingProofImageCount: Int
            get() = MAX_PROOF_IMAGE_COUNT - proofImages.size

        val validProofImageUris: ImmutableList<Uri>
            get() = proofImages
                .filter { it.error == null }
                .map { it.uri }
                .toImmutableList()

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

        data object NavigateBack : SideEffect()

        data class NavigateToJobDetail(
            val jobId: Int,
        ) : SideEffect()

        data class NavigateToPassCard(
            val passCard: RegisterPassCardModel,
        ) : SideEffect()
    }
}

internal fun isContactDateTimeValid(
    dateString: String?,
    timeString: String?,
    now: LocalDateTime = LocalDateTime.now(),
): Boolean {
    val date = dateString?.let(LocalDate::parse) ?: return false
    val time = timeString?.let(LocalTime::parse) ?: return false
    return !LocalDateTime.of(date, time).isAfter(now)
}

sealed interface RegisterUiState {
    data object Idle : RegisterUiState
    data object Loading : RegisterUiState
    data object Empty : RegisterUiState
    data object Success : RegisterUiState
    data class Failure(val msg: String): RegisterUiState
}
