package com.haphap.app.presentation.passcertification

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.passcertification.PassCertificationDetailModel
import com.haphap.app.presentation.passcertification.model.MAX_CERTIFICATION_IMAGE_COUNT
import com.haphap.app.presentation.passcertification.model.PassCertificationImageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface PassCertificationDetailContract {
    @Immutable
    data class State(
        val certification: PassCertificationDetailModel = PassCertificationDetailModel(),
        val isResubmitMode: Boolean = false,
        val newImages: ImmutableList<PassCertificationImageModel> = persistentListOf(),
        val detailUiState: PassCertificationDetailUiState = PassCertificationDetailUiState.Idle,
        val isSubmitting: Boolean = false,
    ) {
        val remainingImageCount: Int
            get() = MAX_CERTIFICATION_IMAGE_COUNT - newImages.size
    }

    sealed interface SideEffect {
        data object NavigateToComplete : SideEffect
        data class OnShowToast(
            val message: String,
            val isAlarm: Boolean = false,
        ) : SideEffect
    }
}

sealed interface PassCertificationDetailUiState {
    data object Idle : PassCertificationDetailUiState
    data object Loading : PassCertificationDetailUiState
    data object Success : PassCertificationDetailUiState
    data class Failure(
        val msg: String,
    ) : PassCertificationDetailUiState
}
