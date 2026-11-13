package com.haphap.app.presentation.register

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.register.NotificationChannelType
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.data.model.register.RegistrationModel
import com.haphap.app.presentation.register.model.MAX_PROOF_IMAGE_COUNT
import com.haphap.app.presentation.register.model.RegisterProofImageModel
import com.haphap.app.presentation.register.type.PassResultStatusType
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

        val selectedResult: PassResultStatusType? = null,
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
                    isContactDateTimeValid && registerInfo.contactedMethod.isNotEmpty()

                RegisterStep.PASS_PROOF -> proofImages.any { it.isUploaded }

                RegisterStep.CONFIRM ->
                    registerInfo.anonymous && registerUiState !is RegisterUiState.Loading

                RegisterStep.COMPLETE -> true
            }

        val isContactDateTimeValid: Boolean
            get() {
                val date = registerInfo.contactedDate?.let(LocalDate::parse) ?: return false
                val time = registerInfo.contactedTime?.let(LocalTime::parse) ?: return false
                return !LocalDateTime.of(date, time).isAfter(LocalDateTime.now())
            }

        val remainingProofImageCount: Int
            get() = MAX_PROOF_IMAGE_COUNT - proofImages.count { it.error == null }

        val validProofImageUris: ImmutableList<Uri>
            get() = proofImages
                .filter { it.isUploaded }
                .map { it.uri }
                .toImmutableList()

        val uploadedProofImageIds: ImmutableList<Long>
            get() = proofImages
                .filter { it.isUploaded }
                .mapNotNull { it.imageId }
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

        data object OpenSingleImagePicker : SideEffect()

        data object OpenMultipleImagePicker : SideEffect()
    }
}

sealed interface RegisterUiState {
    data object Idle : RegisterUiState
    data object Loading : RegisterUiState
    data object Empty : RegisterUiState
    data object Success : RegisterUiState
    data class Failure(val msg: String): RegisterUiState
}
